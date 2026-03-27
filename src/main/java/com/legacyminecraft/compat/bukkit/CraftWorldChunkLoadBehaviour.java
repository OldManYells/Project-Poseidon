package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld chunk-load orchestration.
 */
public final class CraftWorldChunkLoadBehaviour {
    private static final CraftWorldChunkLoadBehaviour INSTANCE = new CraftWorldChunkLoadBehaviour();

    private CraftWorldChunkLoadBehaviour() {
    }

    public static CraftWorldChunkLoadBehaviour getInstance() {
        return INSTANCE;
    }

    public Chunk loadChunk(ChunkProviderServer chunkProviderServer, int chunkX, int chunkZ, boolean generate) {
        if (generate) {
            return chunkProviderServer.getChunkAt(chunkX, chunkZ);
        }

        return loadChunkIfPresentOrDisk(chunkProviderServer, chunkX, chunkZ);
    }

    public Chunk loadChunkIfPresentOrDisk(ChunkProviderServer chunkProviderServer, int chunkX, int chunkZ) {
        chunkProviderServer.unloadQueue.remove(chunkX, chunkZ);
        Chunk loadedChunk = (Chunk) chunkProviderServer.chunks.get(chunkX, chunkZ);
        if (loadedChunk == null) {
            loadedChunk = chunkProviderServer.loadChunk(chunkX, chunkZ);
        }
        return loadedChunk;
    }
}
