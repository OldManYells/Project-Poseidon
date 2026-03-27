package com.legacyminecraft.compat.bukkit;


import java.util.List;
import java.util.Random;

/**
 * Canonical behaviour for CraftBukkit custom chunk-generator orchestration.
 */
public final class CustomChunkGeneratorBehaviour {
    private static final CustomChunkGeneratorBehaviour INSTANCE = new CustomChunkGeneratorBehaviour();

    private CustomChunkGeneratorBehaviour() {
    }

    public static CustomChunkGeneratorBehaviour getInstance() {
        return INSTANCE;
    }

    public Random createRandom(long seed) {
        return new Random(seed);
    }

    public boolean isChunkLoaded() {
        return true;
    }

    public Chunk createChunk(WorldServer worldServer, ChunkGenerator generator, Random random, int chunkX, int chunkZ) {
        random.setSeed((long) chunkX * 341873128712L + (long) chunkZ * 132897987541L);
        byte[] blockTypes = generator.generate(worldServer.getWorld(), random, chunkX, chunkZ);

        Chunk chunk = new Chunk(worldServer, blockTypes, chunkX, chunkZ);
        chunk.initLighting();
        return chunk;
    }

    public void getChunkAt(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        // Intentionally a no-op to preserve legacy compatibility semantics.
    }

    public boolean saveChunks(boolean save, IProgressUpdate progressUpdate) {
        return true;
    }

    public boolean unloadChunks() {
        return false;
    }

    public boolean canSave() {
        return true;
    }

    public byte[] generate(com.legacyminecraft.compat.bukkit.World world, Random random, int chunkX, int chunkZ, ChunkGenerator generator) {
        return generator.generate(world, random, chunkX, chunkZ);
    }

    public Chunk getChunkAt(WorldServer worldServer, ChunkGenerator generator, Random random, int chunkX, int chunkZ) {
        return createChunk(worldServer, generator, random, chunkX, chunkZ);
    }

    public boolean canSpawn(com.legacyminecraft.compat.bukkit.World world, int x, int z, ChunkGenerator generator) {
        return generator.canSpawn(world, x, z);
    }

    public List<BlockPopulator> getDefaultPopulators(com.legacyminecraft.compat.bukkit.World world, ChunkGenerator generator) {
        return generator.getDefaultPopulators(world);
    }
}
