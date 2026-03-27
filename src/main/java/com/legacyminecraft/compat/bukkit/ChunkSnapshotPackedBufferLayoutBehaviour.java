package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for CraftChunkSnapshot packed-buffer layout offsets.
 */
public final class ChunkSnapshotPackedBufferLayoutBehaviour {
    private static final ChunkSnapshotPackedBufferLayoutBehaviour INSTANCE = new ChunkSnapshotPackedBufferLayoutBehaviour();

    private static final int BLOCK_DATA_OFFSET = 32768;
    private static final int BLOCK_LIGHT_OFFSET = BLOCK_DATA_OFFSET + 16384;
    private static final int SKY_LIGHT_OFFSET = BLOCK_LIGHT_OFFSET + 16384;

    private ChunkSnapshotPackedBufferLayoutBehaviour() {
    }

    public static ChunkSnapshotPackedBufferLayoutBehaviour getInstance() {
        return INSTANCE;
    }

    public int getBlockDataOffset() {
        return BLOCK_DATA_OFFSET;
    }

    public int getBlockLightOffset() {
        return BLOCK_LIGHT_OFFSET;
    }

    public int getSkyLightOffset() {
        return SKY_LIGHT_OFFSET;
    }
}
