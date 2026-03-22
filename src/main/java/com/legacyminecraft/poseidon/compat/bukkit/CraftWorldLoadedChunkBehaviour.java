package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Chunk;

/**
 * Canonical behavior for CraftWorld loaded-chunk wrapper projection.
 */
public final class CraftWorldLoadedChunkBehaviour {
    private static final CraftWorldLoadedChunkBehaviour INSTANCE = new CraftWorldLoadedChunkBehaviour();

    private CraftWorldLoadedChunkBehaviour() {
    }

    public static CraftWorldLoadedChunkBehaviour getInstance() {
        return INSTANCE;
    }

    public Chunk[] toBukkitChunks(Object[] loadedChunkValues) {
        Chunk[] bukkitChunks = new org.bukkit.craftbukkit.CraftChunk[loadedChunkValues.length];
        for (int chunkIndex = 0; chunkIndex < loadedChunkValues.length; chunkIndex++) {
            net.minecraft.server.Chunk loadedChunk = (net.minecraft.server.Chunk) loadedChunkValues[chunkIndex];
            bukkitChunks[chunkIndex] = loadedChunk.bukkitChunk;
        }
        return bukkitChunks;
    }
}
