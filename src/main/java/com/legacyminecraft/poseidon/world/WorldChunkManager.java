package com.legacyminecraft.poseidon.world;

/**
 * Canonical biome/climate manager scaffold for migrated chunk snapshot behaviour.
 */
public class WorldChunkManager {
    public double[] temperature = new double[256];
    public double[] rain = new double[256];

    public WorldChunkManager() {
    }

    public WorldChunkManager(World world) {
    }

    public BiomeBase[] getBiomeData(int x, int z, int width, int depth) {
        BiomeBase[] data = new BiomeBase[width * depth];
        BiomeBase fallback = new BiomeBase();
        for (int i = 0; i < data.length; i++) {
            data[i] = fallback;
        }
        return data;
    }
}
