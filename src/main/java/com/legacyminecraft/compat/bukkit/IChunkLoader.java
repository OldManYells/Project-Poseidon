package com.legacyminecraft.compat.bukkit;

/**
 * Compat chunk-loader contract scaffold.
 */
public interface IChunkLoader {
    Chunk loadChunk(World world, int chunkX, int chunkZ);

    void saveChunk(World world, Chunk chunk);

    void saveChunkNOP(World world, Chunk chunk);
}
