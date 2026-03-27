package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat chunk-provider contract scaffold.
 */
public interface IChunkProvider {
    boolean isChunkLoaded(int chunkX, int chunkZ);

    Chunk getOrCreateChunk(int chunkX, int chunkZ);

    Chunk getChunkAt(int chunkX, int chunkZ);

    void getChunkAt(IChunkProvider requester, int chunkX, int chunkZ);

    boolean saveChunks(boolean save, IProgressUpdate progressUpdate);

    boolean unloadChunks();

    boolean canSave();
}

