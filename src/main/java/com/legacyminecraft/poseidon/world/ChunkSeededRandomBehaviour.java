package com.legacyminecraft.poseidon.world;

import java.util.Random;

/**
 * Canonical behaviour for chunk-coordinate seeded random generation.
 */
public final class ChunkSeededRandomBehaviour {
    private static final ChunkSeededRandomBehaviour INSTANCE = new ChunkSeededRandomBehaviour();

    private ChunkSeededRandomBehaviour() {
    }

    public static ChunkSeededRandomBehaviour getInstance() {
        return INSTANCE;
    }

    public Random create(long worldSeed, int chunkX, int chunkZ, long salt) {
        return new Random(
                worldSeed
                        + (long) (chunkX * chunkX * 4987142)
                        + (long) (chunkX * 5947611)
                        + (long) (chunkZ * chunkZ) * 4392871L
                        + (long) (chunkZ * 389711)
                        ^ salt
        );
    }
}

