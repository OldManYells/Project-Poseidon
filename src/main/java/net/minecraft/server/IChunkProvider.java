package net.minecraft.server;

import com.legacyminecraft.poseidon.world.chunk.ChunkProviderContract;

public interface IChunkProvider extends ChunkProviderContract {

    boolean isChunkLoaded(int i, int j);

    Chunk getOrCreateChunk(int i, int j);

    Chunk getChunkAt(int i, int j);

    void getChunkAt(IChunkProvider ichunkprovider, int i, int j);

    boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate);

    boolean unloadChunks();

    boolean canSave();

    default void populate(IChunkProvider provider, int chunkX, int chunkZ) {
        this.getChunkAt(provider, chunkX, chunkZ);
    }
}
