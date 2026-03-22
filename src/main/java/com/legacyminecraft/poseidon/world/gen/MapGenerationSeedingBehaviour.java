package com.legacyminecraft.poseidon.world.gen;

import net.minecraft.server.World;

import java.util.Random;

public final class MapGenerationSeedingBehaviour {
    private static final MapGenerationSeedingBehaviour INSTANCE = new MapGenerationSeedingBehaviour();

    private MapGenerationSeedingBehaviour() {
    }

    public static MapGenerationSeedingBehaviour getInstance() {
        return INSTANCE;
    }

    public interface GenerationCallback {
        void generate(World world, int chunkX, int chunkZ, int originX, int originZ, byte[] blockData);
    }

    public void run(int radius, Random random, World world, int originX, int originZ, byte[] blockData, GenerationCallback callback) {
        random.setSeed(world.getSeed());
        long xSeed = random.nextLong() / 2L * 2L + 1L;
        long zSeed = random.nextLong() / 2L * 2L + 1L;

        for (int chunkX = originX - radius; chunkX <= originX + radius; ++chunkX) {
            for (int chunkZ = originZ - radius; chunkZ <= originZ + radius; ++chunkZ) {
                random.setSeed((long) chunkX * xSeed + (long) chunkZ * zSeed ^ world.getSeed());
                callback.generate(world, chunkX, chunkZ, originX, originZ, blockData);
            }
        }
    }
}
