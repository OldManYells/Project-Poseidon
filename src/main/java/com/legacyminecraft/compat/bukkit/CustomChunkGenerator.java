package com.legacyminecraft.compat.bukkit;

import java.util.Random;

/**
 * Canonical compat custom chunk-provider scaffold.
 */
public class CustomChunkGenerator extends NormalChunkGenerator {
    private final Random random;
    private final ChunkGenerator generator;
    private final WorldServer worldServer;

    public CustomChunkGenerator(WorldServer worldServer, long seed, ChunkGenerator generator) {
        super(worldServer, seed);
        this.worldServer = worldServer;
        this.random = new Random(seed);
        this.generator = generator;
    }

    @Override
    public Chunk getOrCreateChunk(int chunkX, int chunkZ) {
        byte[] blockTypes = generator.generate(worldServer.getWorld(), random, chunkX, chunkZ);
        Chunk chunk = new Chunk(worldServer, blockTypes, chunkX, chunkZ);
        chunk.initLighting();
        return chunk;
    }
}

