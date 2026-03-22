package com.legacyminecraft.poseidon.world.chunk;

/**
 * Canonical behaviour for chunk-provider save/unload policy decisions.
 */
public final class ChunkProviderPersistencePolicyBehaviour {
    private static final ChunkProviderPersistencePolicyBehaviour INSTANCE = new ChunkProviderPersistencePolicyBehaviour();

    private ChunkProviderPersistencePolicyBehaviour() {
    }

    public static ChunkProviderPersistencePolicyBehaviour getInstance() {
        return INSTANCE;
    }

    public int maxChunksPerIncrementalSavePass() {
        return 24;
    }

    public int maxChunksPerUnloadPass() {
        return 100;
    }

    public boolean shouldWriteChunkMetadata(boolean forceSave, boolean chunkNeverSaveFlag) {
        return forceSave && !chunkNeverSaveFlag;
    }

    public boolean shouldStopIncrementalSave(int savedChunks, boolean forceSave) {
        return !forceSave && savedChunks == this.maxChunksPerIncrementalSavePass();
    }

    public boolean shouldFlushChunkLoader(boolean forceSave, Object chunkLoader) {
        return forceSave && chunkLoader != null;
    }

    public boolean hasQueuedUnloads(boolean unloadQueueEmpty) {
        return !unloadQueueEmpty;
    }
}
