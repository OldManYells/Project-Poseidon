package com.legacyminecraft.poseidon.world;

/**
 * World-local overworld generator scaffold.
 */
public class ChunkProviderGenerate implements IChunkProvider {
    public ChunkProviderGenerate(World world, long seed) {
    }

    @Override
    public boolean isChunkLoaded(int i, int j) {
        return true;
    }

    @Override
    public Chunk getOrCreateChunk(int i, int j) {
        return new Chunk(new World(), i, j);
    }

    @Override
    public Chunk getChunkAt(int i, int j) {
        return new Chunk(new World(), i, j);
    }

    @Override
    public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
    }

    @Override
    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        return true;
    }

    @Override
    public boolean unloadChunks() {
        return false;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    @Override
    public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
    }
}
