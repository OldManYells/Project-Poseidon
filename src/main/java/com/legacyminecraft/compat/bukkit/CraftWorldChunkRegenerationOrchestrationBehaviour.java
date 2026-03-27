package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behaviour for CraftWorld chunk-regeneration orchestration wrapper glue.
 */
public final class CraftWorldChunkRegenerationOrchestrationBehaviour {
    public interface ChunkRegenerationActions {
        boolean unloadChunk(int x, int z, boolean save, boolean safe);

        com.legacyminecraft.compat.bukkit.Chunk resolveRegeneratedChunk(int x, int z);

        void postProcessLoadedChunk(com.legacyminecraft.compat.bukkit.Chunk chunk, int x, int z);

        void refreshChunk(int x, int z);
    }

    private static final CraftWorldChunkRegenerationOrchestrationBehaviour INSTANCE =
            new CraftWorldChunkRegenerationOrchestrationBehaviour();

    private CraftWorldChunkRegenerationOrchestrationBehaviour() {
    }

    public static CraftWorldChunkRegenerationOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean regenerateChunk(int x, int z, ChunkRegenerationActions actions) {
        actions.unloadChunk(x, z, false, false);
        com.legacyminecraft.compat.bukkit.Chunk chunk = actions.resolveRegeneratedChunk(x, z);
        actions.postProcessLoadedChunk(chunk, x, z);
        actions.refreshChunk(x, z);
        return chunk != null;
    }
}
