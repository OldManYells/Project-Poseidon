package com.legacyminecraft.poseidon.api;

import com.legacyminecraft.poseidon.world.core.Chunk;
import com.legacyminecraft.poseidon.world.core.World;

import java.io.IOException;

public interface IChunkLoader {

    Chunk a(World world, int i, int j) throws IOException;

    void a(World world, Chunk chunk);

    void b(World world, Chunk chunk);

    void a();

    void b();
}
