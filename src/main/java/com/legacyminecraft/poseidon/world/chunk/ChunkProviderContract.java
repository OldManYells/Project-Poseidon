package com.legacyminecraft.poseidon.world.chunk;


import com.legacyminecraft.poseidon.world.Chunk;
import com.legacyminecraft.poseidon.world.IProgressUpdate;

public interface ChunkProviderContract {
    boolean isChunkLoaded(int chunkX, int chunkZ);

    Chunk getOrCreateChunk(int chunkX, int chunkZ);

    Chunk getChunkAt(int chunkX, int chunkZ);

    void populate(IChunkProvider provider, int chunkX, int chunkZ);

    boolean saveChunks(boolean forceSave, IProgressUpdate progressUpdate);

    boolean unloadChunks();

    boolean canSave();
}
