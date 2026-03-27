package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld biome lookup wrapper glue.
 */
public final class CraftWorldBiomeLookupBehaviour {
    private static final CraftWorldBiomeLookupBehaviour INSTANCE = new CraftWorldBiomeLookupBehaviour();

    private CraftWorldBiomeLookupBehaviour() {
    }

    public static CraftWorldBiomeLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public Biome getBiome(WorldServer worldServer, int x, int z, BiomeConversionBehaviour biomeConversionBehaviour) {
        BiomeBase base = worldServer.getWorldChunkManager().getBiome(x, z);
        return biomeConversionBehaviour.biomeBaseToBiome(base);
    }
}
