package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical behaviour for iterating chunk ranges around a center point.
 */
public final class PlayerChunkIterationBehaviour {
    private static final PlayerChunkIterationBehaviour INSTANCE = new PlayerChunkIterationBehaviour();

    private PlayerChunkIterationBehaviour() {
    }

    public static PlayerChunkIterationBehaviour getInstance() {
        return INSTANCE;
    }

    public void forEachChunkInViewRange(int centerChunkX, int centerChunkZ, int viewRadius, ChunkConsumer consumer) {
        for (int chunkX = centerChunkX - viewRadius; chunkX <= centerChunkX + viewRadius; ++chunkX) {
            for (int chunkZ = centerChunkZ - viewRadius; chunkZ <= centerChunkZ + viewRadius; ++chunkZ) {
                consumer.accept(chunkX, chunkZ);
            }
        }
    }

    public interface ChunkConsumer {
        void accept(int chunkX, int chunkZ);
    }
}
