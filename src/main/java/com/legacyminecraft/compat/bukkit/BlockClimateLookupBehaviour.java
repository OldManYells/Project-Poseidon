package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block biome and climate lookup helpers.
 */
public final class BlockClimateLookupBehaviour {
    private static final BlockClimateLookupBehaviour INSTANCE = new BlockClimateLookupBehaviour();

    private BlockClimateLookupBehaviour() {
    }

    public static BlockClimateLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public Biome getBiome(com.legacyminecraft.compat.bukkit.World world, int x, int z, BiomeConversionBehaviour conversionBehaviour) {
        BiomeBase biomeBase = world.getWorldChunkManager().getBiome(x, z);
        return conversionBehaviour.biomeBaseToBiome(biomeBase);
    }

    public double getTemperature(World world, int x, int z) {
        return world.getTemperature(x, z);
    }

    public double getHumidity(World world, int x, int z) {
        return world.getHumidity(x, z);
    }
}
