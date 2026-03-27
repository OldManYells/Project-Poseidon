package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftBukkit biome base -> Bukkit biome conversion.
 */
public final class BiomeConversionBehaviour {
    private static final BiomeConversionBehaviour INSTANCE = new BiomeConversionBehaviour();

    private BiomeConversionBehaviour() {
    }

    public static BiomeConversionBehaviour getInstance() {
        return INSTANCE;
    }

    public Biome biomeBaseToBiome(BiomeBase biomeBase) {
        if (biomeBase == BiomeBase.RAINFOREST) {
            return Biome.RAINFOREST;
        } else if (biomeBase == BiomeBase.SWAMPLAND) {
            return Biome.SWAMPLAND;
        } else if (biomeBase == BiomeBase.SEASONAL_FOREST) {
            return Biome.SEASONAL_FOREST;
        } else if (biomeBase == BiomeBase.FOREST) {
            return Biome.FOREST;
        } else if (biomeBase == BiomeBase.SAVANNA) {
            return Biome.SAVANNA;
        } else if (biomeBase == BiomeBase.SHRUBLAND) {
            return Biome.SHRUBLAND;
        } else if (biomeBase == BiomeBase.TAIGA) {
            return Biome.TAIGA;
        } else if (biomeBase == BiomeBase.DESERT) {
            return Biome.DESERT;
        } else if (biomeBase == BiomeBase.PLAINS) {
            return Biome.PLAINS;
        } else if (biomeBase == BiomeBase.ICE_DESERT) {
            return Biome.ICE_DESERT;
        } else if (biomeBase == BiomeBase.TUNDRA) {
            return Biome.TUNDRA;
        } else if (biomeBase == BiomeBase.HELL) {
            return Biome.HELL;
        } else if (biomeBase == BiomeBase.SKY) {
            return Biome.SKY;
        }

        return null;
    }
}
