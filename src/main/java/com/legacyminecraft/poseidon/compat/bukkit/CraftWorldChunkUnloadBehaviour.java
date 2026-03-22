package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkProviderServer;

/**
 * Canonical behavior for CraftWorld chunk-unload request and unload orchestration.
 */
public final class CraftWorldChunkUnloadBehaviour {
    private static final CraftWorldChunkUnloadBehaviour INSTANCE = new CraftWorldChunkUnloadBehaviour();

    private CraftWorldChunkUnloadBehaviour() {
    }

    public static CraftWorldChunkUnloadBehaviour getInstance() {
        return INSTANCE;
    }

    public void queueUnload(ChunkProviderServer chunkProviderServer, int chunkX, int chunkZ) {
        chunkProviderServer.queueUnload(chunkX, chunkZ);
    }

    public boolean unloadChunk(ChunkProviderServer chunkProviderServer, int chunkX, int chunkZ, boolean saveChunk) {
        Chunk chunk = chunkProviderServer.getOrCreateChunk(chunkX, chunkZ);

        if (saveChunk && !chunk.isEmpty()) {
            chunk.removeEntities();
            chunkProviderServer.saveChunk(chunk);
            chunkProviderServer.saveChunkNOP(chunk);
        }

        chunkProviderServer.unloadQueue.remove(chunkX, chunkZ);
        chunkProviderServer.chunks.remove(chunkX, chunkZ);
        chunkProviderServer.chunkList.remove(chunk);
        return true;
    }
}
