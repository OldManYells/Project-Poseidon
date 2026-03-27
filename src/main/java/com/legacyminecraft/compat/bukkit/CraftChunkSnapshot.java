package com.legacyminecraft.compat.bukkit;

/**
 * Canonical CraftChunk snapshot factory scaffold.
 */
public final class CraftChunkSnapshot {
    private CraftChunkSnapshot() {
    }

    public static ChunkSnapshot create(
            int chunkX,
            int chunkZ,
            String worldName,
            long fullTime,
            byte[] chunkBuffer,
            byte[] heightMap,
            net.minecraft.server.BiomeBase[] biomes,
            double[] temperatures,
            double[] rainfall
    ) {
        return new BasicChunkSnapshot();
    }

    private static final class BasicChunkSnapshot implements ChunkSnapshot {
    }
}
