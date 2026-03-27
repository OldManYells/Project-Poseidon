package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat world-provider scaffold.
 */
public class WorldProvider {
    public static WorldProvider byDimension(int dimensionId) {
        return new WorldProvider();
    }

    public IChunkProvider getChunkProvider() {
        return new NormalChunkGenerator(new WorldServer(), 0L);
    }

    public boolean canSpawn(int x, int z) {
        return true;
    }
}
