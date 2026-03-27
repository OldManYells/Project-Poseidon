package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for chunk snapshot raw climate lookups.
 */
public final class ChunkSnapshotClimateLookupBehaviour {
    private static final ChunkSnapshotClimateLookupBehaviour INSTANCE = new ChunkSnapshotClimateLookupBehaviour();

    private ChunkSnapshotClimateLookupBehaviour() {
    }

    public static ChunkSnapshotClimateLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public double getTemperature(ChunkSnapshotDataAccessBehaviour dataAccessBehaviour, double[] biomeTemperature, int x, int z) {
        return dataAccessBehaviour.readClimateValue(biomeTemperature, x, z);
    }

    public double getRainfall(ChunkSnapshotDataAccessBehaviour dataAccessBehaviour, double[] biomeRainfall, int x, int z) {
        return dataAccessBehaviour.readClimateValue(biomeRainfall, x, z);
    }
}
