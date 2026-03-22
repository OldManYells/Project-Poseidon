package com.legacyminecraft.poseidon.world.gen;

/**
 * Canonical bootstrap behaviour for chunk-generator seed/buffer setup.
 */
public final class ChunkGeneratorBootstrapBehaviour {
    private static final ChunkGeneratorBootstrapBehaviour INSTANCE = new ChunkGeneratorBootstrapBehaviour();

    private ChunkGeneratorBootstrapBehaviour() {
    }

    public static ChunkGeneratorBootstrapBehaviour getInstance() {
        return INSTANCE;
    }

    public long chunkSeed(int chunkX, int chunkZ) {
        return (long) chunkX * 341873128712L + (long) chunkZ * 132897987541L;
    }

    public byte[] createChunkBlockBuffer() {
        return new byte['\u8000'];
    }
}
