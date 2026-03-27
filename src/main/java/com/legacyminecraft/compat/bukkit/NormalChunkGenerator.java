package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat normal chunk-provider scaffold.
 */
public class NormalChunkGenerator implements IChunkProvider {
    public NormalChunkGenerator(WorldServer world, long seed) {
    }

    @Override
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public Chunk getOrCreateChunk(int chunkX, int chunkZ) {
        return new Chunk();
    }

    @Override
    public Chunk getChunkAt(int chunkX, int chunkZ) {
        return new Chunk();
    }

    @Override
    public void getChunkAt(IChunkProvider requester, int chunkX, int chunkZ) {
    }

    @Override
    public boolean saveChunks(boolean save, IProgressUpdate progressUpdate) {
        return true;
    }

    @Override
    public boolean unloadChunks() {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }
}

