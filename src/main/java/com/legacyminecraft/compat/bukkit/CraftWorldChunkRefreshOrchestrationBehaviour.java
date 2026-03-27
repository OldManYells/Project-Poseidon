package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for CraftWorld chunk-refresh orchestration wrapper glue.
 */
public final class CraftWorldChunkRefreshOrchestrationBehaviour {
    public interface ChunkRefreshActions {
        boolean isChunkLoaded(int x, int z);

        void notifyChunkRefresh(int x, int z);
    }

    private static final CraftWorldChunkRefreshOrchestrationBehaviour INSTANCE =
            new CraftWorldChunkRefreshOrchestrationBehaviour();

    private CraftWorldChunkRefreshOrchestrationBehaviour() {
    }

    public static CraftWorldChunkRefreshOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean refreshChunk(int x, int z, ChunkRefreshActions actions) {
        if (!actions.isChunkLoaded(x, z)) {
            return false;
        }
        actions.notifyChunkRefresh(x, z);
        return true;
    }
}
