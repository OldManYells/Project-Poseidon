package net.minecraft.server;

public interface IChunkProvider extends com.legacyminecraft.poseidon.world.IChunkProvider {

    boolean isChunkLoaded(int i, int j);

    Chunk getOrCreateChunk(int i, int j);

    Chunk getChunkAt(int i, int j);

    void getChunkAt(IChunkProvider ichunkprovider, int i, int j);

    @Override
    default void getChunkAt(com.legacyminecraft.poseidon.world.IChunkProvider ichunkprovider, int i, int j) {
        this.getChunkAt((IChunkProvider) ichunkprovider, i, j);
    }

    boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate);

    @Override
    default boolean saveChunks(boolean flag, com.legacyminecraft.poseidon.world.IProgressUpdate iprogressupdate) {
        return this.saveChunks(flag, (IProgressUpdate) iprogressupdate);
    }

    boolean unloadChunks();

    boolean canSave();

    default void populate(com.legacyminecraft.poseidon.world.IChunkProvider provider, int chunkX, int chunkZ) {
        this.getChunkAt((IChunkProvider) provider, chunkX, chunkZ);
    }
}
