package net.minecraft.server;

import com.legacyminecraft.poseidon.world.chunk.ChunkLoaderContract;

import java.io.IOException;

public interface IChunkLoader extends ChunkLoaderContract {

    Chunk a(World world, int i, int j) throws IOException;

    void a(World world, Chunk chunk);

    void b(World world, Chunk chunk);

    void a();

    void b();

    default Chunk loadChunk(World world, int chunkX, int chunkZ) throws IOException {
        return this.a(world, chunkX, chunkZ);
    }

    default void saveChunk(World world, Chunk chunk) {
        this.a(world, chunk);
    }

    default void saveChunkExtraData(World world, Chunk chunk) {
        this.b(world, chunk);
    }

    default void flush() {
        this.a();
    }

    default void close() {
        this.b();
    }
}
