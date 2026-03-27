package com.legacyminecraft.poseidon.world;

/**
 * World-local nether chunk provider scaffold.
 */
public class ChunkProviderHell implements IChunkProvider {
    private final World world;
    private final long seed;

    public ChunkProviderHell(World world, long seed) {
        this.world = world;
        this.seed = seed;
    }

    @Override
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return true;
    }

    @Override
    public Chunk getOrCreateChunk(int chunkX, int chunkZ) {
        return new Chunk(world);
    }

    @Override
    public Chunk getChunkAt(int chunkX, int chunkZ) {
        return new Chunk(world);
    }

    @Override
    public void getChunkAt(com.legacyminecraft.compat.bukkit.IChunkProvider requester, int chunkX, int chunkZ) {
    }

    @Override
    public boolean saveChunks(boolean save, com.legacyminecraft.compat.bukkit.IProgressUpdate progressUpdate) {
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

    public long getSeed() {
        return seed;
    }
}
