package com.legacyminecraft.compat.bukkit;


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

    public Chunk[] getLoadedChunks(ChunkProviderServer chunkProviderServer) {
        Object[] loadedChunkValues = chunkProviderServer.chunks.values().toArray();
        return this.toBukkitChunks(loadedChunkValues);
    }

    public Chunk[] toBukkitChunks(Object[] loadedChunkValues) {
        Chunk[] bukkitChunks = new CraftChunk[loadedChunkValues.length];
        for (int chunkIndex = 0; chunkIndex < loadedChunkValues.length; chunkIndex++) {
            com.legacyminecraft.compat.bukkit.Chunk loadedChunk = (com.legacyminecraft.compat.bukkit.Chunk) loadedChunkValues[chunkIndex];
            bukkitChunks[chunkIndex] = loadedChunk.bukkitChunk;
        }
        return bukkitChunks;
    }
}
