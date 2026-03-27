package com.legacyminecraft.compat.bukkit;

/**
 * Empty chunk snapshot - nothing but air blocks, but can include valid biome data.
 */
public final class CraftChunkEmptySnapshot implements ChunkSnapshot {
    public CraftChunkEmptySnapshot(
            int x,
            int z,
            String worldName,
            long time,
            net.minecraft.server.BiomeBase[] biome,
            double[] biomeTemp,
            double[] biomeRain
    ) {
    }

    public int getBlockTypeId(int x, int y, int z) {
        return 0;
    }

    public int getBlockData(int x, int y, int z) {
        return 0;
    }

    public int getBlockSkyLight(int x, int y, int z) {
        return 15;
    }

    public int getBlockEmittedLight(int x, int y, int z) {
        return 0;
    }

    public int getHighestBlockYAt(int x, int z) {
        return 0;
    }
}
