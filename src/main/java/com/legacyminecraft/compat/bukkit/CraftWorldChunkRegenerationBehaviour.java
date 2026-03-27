package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld chunk regeneration source-chunk resolution.
 */
public final class CraftWorldChunkRegenerationBehaviour {
    private static final CraftWorldChunkRegenerationBehaviour INSTANCE = new CraftWorldChunkRegenerationBehaviour();

    private CraftWorldChunkRegenerationBehaviour() {
    }

    public static CraftWorldChunkRegenerationBehaviour getInstance() {
        return INSTANCE;
    }

    public Chunk resolveRegeneratedChunk(ChunkProviderServer chunkProviderServer, int chunkX, int chunkZ) {
        chunkProviderServer.unloadQueue.remove(chunkX, chunkZ);
        if (chunkProviderServer.chunkProvider == null) {
            return chunkProviderServer.emptyChunk;
        }
        return chunkProviderServer.chunkProvider.getOrCreateChunk(chunkX, chunkZ);
    }
}
