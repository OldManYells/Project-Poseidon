package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical behaviour for spiral chunk traversal used by player chunk loading.
 */
public final class PlayerChunkSpiralLoadBehaviour {
    private static final PlayerChunkSpiralLoadBehaviour INSTANCE = new PlayerChunkSpiralLoadBehaviour();
    private static final int[][] SPIRAL_DIRECTIONS = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

    private PlayerChunkSpiralLoadBehaviour() {
    }

    public static PlayerChunkSpiralLoadBehaviour getInstance() {
        return INSTANCE;
    }

    public void forEachSpiralChunk(int centerChunkX, int centerChunkZ, int viewRadius, ChunkConsumer consumer) {
        consumer.accept(centerChunkX, centerChunkZ);

        int directionIndex = 0;
        int offsetX = 0;
        int offsetZ = 0;

        for (int stepLength = 1; stepLength <= viewRadius * 2; ++stepLength) {
            for (int leg = 0; leg < 2; ++leg) {
                int[] direction = SPIRAL_DIRECTIONS[directionIndex++ % 4];

                for (int step = 0; step < stepLength; ++step) {
                    offsetX += direction[0];
                    offsetZ += direction[1];
                    consumer.accept(centerChunkX + offsetX, centerChunkZ + offsetZ);
                }
            }
        }

        directionIndex %= 4;

        for (int step = 0; step < viewRadius * 2; ++step) {
            offsetX += SPIRAL_DIRECTIONS[directionIndex][0];
            offsetZ += SPIRAL_DIRECTIONS[directionIndex][1];
            consumer.accept(centerChunkX + offsetX, centerChunkZ + offsetZ);
        }
    }

    public interface ChunkConsumer {
        void accept(int chunkX, int chunkZ);
    }
}
