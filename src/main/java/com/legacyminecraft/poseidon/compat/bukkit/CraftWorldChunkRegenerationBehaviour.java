package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkProviderServer;

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
