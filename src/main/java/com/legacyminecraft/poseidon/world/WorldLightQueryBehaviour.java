package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Chunk;
import net.minecraft.server.EnumSkyBlock;
import net.minecraft.server.World;

/**
 * Canonical behaviour for world light-value query policy.
 */
public final class WorldLightQueryBehaviour {
    private static final WorldLightQueryBehaviour INSTANCE = new WorldLightQueryBehaviour();

    private WorldLightQueryBehaviour() {
    }

    public static WorldLightQueryBehaviour getInstance() {
        return INSTANCE;
    }

    public int queryLightValue(World world, EnumSkyBlock lightLayer, int x, int y, int z, int outOfBoundsFallback) {
        int clampedY = this.clampY(y);
        if (!this.isWithinBounds(x, z) || !this.isWithinBuildHeight(clampedY)) {
            return outOfBoundsFallback;
        }

        int chunkX = x >> 4;
        int chunkZ = z >> 4;
        if (!world.chunkProvider.isChunkLoaded(chunkX, chunkZ)) {
            return 0;
        }

        Chunk chunk = world.getChunkAt(chunkX, chunkZ);
        return chunk.a(lightLayer, x & 15, clampedY, z & 15);
    }

    public float mapBrightness(float[] brightnessTable, int lightLevel) {
        return brightnessTable[lightLevel];
    }

    private int clampY(int y) {
        if (y < 0) {
            return 0;
        }

        if (y >= 128) {
            return 127;
        }

        return y;
    }

    private boolean isWithinBounds(int x, int z) {
        return x >= -32000000 && z >= -32000000 && x < 32000000 && z <= 32000000;
    }

    private boolean isWithinBuildHeight(int y) {
        return y >= 0 && y < 128;
    }
}
