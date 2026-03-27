package com.legacyminecraft.poseidon.world;

/**
 * World-local chunk provider alias.
 */
public interface IChunkProvider {
    boolean isChunkLoaded(int i, int j);

    Chunk getOrCreateChunk(int i, int j);

    Chunk getChunkAt(int i, int j);

    void getChunkAt(IChunkProvider ichunkprovider, int i, int j);

    boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate);

    boolean unloadChunks();

    boolean canSave();

    void populate(IChunkProvider provider, int chunkX, int chunkZ);
}
