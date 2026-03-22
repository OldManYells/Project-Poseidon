package com.legacyminecraft.poseidon.world.chunk;

import net.minecraft.server.Chunk;
import net.minecraft.server.World;

import java.io.IOException;

public interface ChunkLoaderContract {
    Chunk loadChunk(World world, int chunkX, int chunkZ) throws IOException;

    void saveChunk(World world, Chunk chunk);

    void saveChunkExtraData(World world, Chunk chunk);

    void flush();

    void close();
}
