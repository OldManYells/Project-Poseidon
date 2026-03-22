package com.legacyminecraft.poseidon.world;

import net.minecraft.server.BiomeBase;
import net.minecraft.server.Block;
import net.minecraft.server.ChunkProviderSky;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.World;
import net.minecraft.server.WorldChunkManagerHell;

public final class SkyWorldProviderBehaviour {
    private static final SkyWorldProviderBehaviour INSTANCE = new SkyWorldProviderBehaviour();

    private SkyWorldProviderBehaviour() {
    }

    public static SkyWorldProviderBehaviour getInstance() {
        return INSTANCE;
    }

    public SkyConfiguration configure(World world) {
        return new SkyConfiguration(new WorldChunkManagerHell(BiomeBase.SKY, 0.5D, 0.0D), 1);
    }

    public IChunkProvider createChunkProvider(World world) {
        return new ChunkProviderSky(world, world.getSeed());
    }

    public float celestialAngle(long i, float f) {
        return 0.0F;
    }

    public boolean canSpawn(World world, int i, int j) {
        int k = world.a(i, j);
        return k != 0 && Block.byId[k].material.isSolid();
    }

    public static final class SkyConfiguration {
        public final WorldChunkManagerHell chunkManager;
        public final int dimension;

        SkyConfiguration(WorldChunkManagerHell chunkManager, int dimension) {
            this.chunkManager = chunkManager;
            this.dimension = dimension;
        }
    }
}
