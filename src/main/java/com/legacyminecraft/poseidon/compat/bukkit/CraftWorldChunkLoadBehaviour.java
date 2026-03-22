package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkProviderServer;

/**
 * Canonical behavior for CraftWorld non-generating chunk-load orchestration.
 */
public final class CraftWorldChunkLoadBehaviour {
    private static final CraftWorldChunkLoadBehaviour INSTANCE = new CraftWorldChunkLoadBehaviour();

    private CraftWorldChunkLoadBehaviour() {
    }

    public static CraftWorldChunkLoadBehaviour getInstance() {
        return INSTANCE;
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
