package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for chunk snapshot biome lookup and resolver wiring.
 */
public final class ChunkSnapshotBiomeLookupBehaviour {
    private static final ChunkSnapshotBiomeLookupBehaviour INSTANCE = new ChunkSnapshotBiomeLookupBehaviour();

    private ChunkSnapshotBiomeLookupBehaviour() {
    }

    public static ChunkSnapshotBiomeLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public Biome getBiome(
            ChunkSnapshotDataAccessBehaviour dataAccessBehaviour,
            BiomeBase[] biomeData,
            int x,
            int z,
            final BiomeConversionBehaviour conversionBehaviour
    ) {
        return dataAccessBehaviour.readBiome(
                biomeData,
                x,
                z,
                new ChunkSnapshotDataAccessBehaviour.BiomeResolver() {
                    public Biome resolve(BiomeBase biomeBase) {
                        return conversionBehaviour.biomeBaseToBiome(biomeBase);
                    }
                }
        );
    }
}
