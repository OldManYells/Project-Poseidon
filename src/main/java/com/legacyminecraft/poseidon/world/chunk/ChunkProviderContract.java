package com.legacyminecraft.poseidon.world.chunk;

import net.minecraft.server.Chunk;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.IProgressUpdate;

public interface ChunkProviderContract {
    boolean isChunkLoaded(int chunkX, int chunkZ);

    Chunk getOrCreateChunk(int chunkX, int chunkZ);

    Chunk getChunkAt(int chunkX, int chunkZ);

    void populate(IChunkProvider provider, int chunkX, int chunkZ);

    boolean saveChunks(boolean forceSave, IProgressUpdate progressUpdate);

    boolean unloadChunks();

    boolean canSave();
}
