package com.legacyminecraft.poseidon.world;

import java.util.Arrays;
import java.util.Random;

/**
 * Canonical behaviour for deterministic empty-chunk helper logic.
 */
public final class EmptyChunkBehaviour {
    private static final EmptyChunkBehaviour INSTANCE = new EmptyChunkBehaviour();

    private EmptyChunkBehaviour() {
    }

    public static EmptyChunkBehaviour getInstance() {
        return INSTANCE;
    }

    public int zeroFillChunkData(byte[] buffer, int offset, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        int xSize = maxX - minX;
        int ySize = maxY - minY;
        int zSize = maxZ - minZ;
        int blockCount = xSize * ySize * zSize;
        int encodedLength = blockCount + blockCount / 2 * 3;

        Arrays.fill(buffer, offset, offset + encodedLength, (byte) 0);
        return encodedLength;
    }

    public Random createChunkRandom(long worldSeed, int chunkX, int chunkZ, long randomSalt) {
        long mixedSeed = worldSeed
                + (long) (chunkX * chunkX * 4987142)
                + (long) (chunkX * 5947611)
                + (long) (chunkZ * chunkZ) * 4392871L
                + (long) (chunkZ * 389711);
        return new Random(mixedSeed ^ randomSalt);
    }
}
