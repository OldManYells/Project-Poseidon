package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Block;
import net.minecraft.server.ChunkProviderGenerate;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.World;
import net.minecraft.server.WorldChunkManager;
import net.minecraft.server.WorldProvider;
import net.minecraft.server.WorldProviderHell;
import net.minecraft.server.WorldProviderNormal;
import net.minecraft.server.WorldProviderSky;

public final class WorldProviderBehaviour {
    private static final WorldProviderBehaviour INSTANCE = new WorldProviderBehaviour();

    private WorldProviderBehaviour() {
    }

    public static WorldProviderBehaviour getInstance() {
        return INSTANCE;
    }

    public WorldChunkManager createDefaultChunkManager(World world) {
        return new WorldChunkManager(world);
    }

    public float[] buildLightBrightnessTable(float floor) {
        float[] brightness = new float[16];

        for (int i = 0; i <= 15; ++i) {
            float f1 = 1.0F - (float) i / 15.0F;
            brightness[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * (1.0F - floor) + floor;
        }

        return brightness;
    }

    public IChunkProvider createOverworldChunkProvider(World world) {
        return new ChunkProviderGenerate(world, world.getSeed());
    }

    public boolean canSpawnOnSand(World world, int i, int j) {
        int k = world.a(i, j);
        return k == Block.SAND.id;
    }

    public float computeCelestialAngle(long i, float f) {
        int j = (int) (i % 24000L);
        float f1 = ((float) j + f) / 24000.0F - 0.25F;

        if (f1 < 0.0F) {
            ++f1;
        }

        if (f1 > 1.0F) {
            --f1;
        }

        float f2 = f1;
        f1 = 1.0F - (float) ((Math.cos((double) f1 * 3.141592653589793D) + 1.0D) / 2.0D);
        return f2 + (f1 - f2) / 3.0F;
    }

    public boolean hasSkyLight() {
        return true;
    }

    public WorldProvider byDimension(int i) {
        return i == -1 ? new WorldProviderHell() : (i == 0 ? new WorldProviderNormal() : (i == 1 ? new WorldProviderSky() : null));
    }
}
