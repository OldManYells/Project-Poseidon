package com.legacyminecraft.poseidon.world;

/**
 * Canonical region sector allocation behaviour.
 */
public final class RegionSectorAllocationBehaviour {
    private static final RegionSectorAllocationBehaviour INSTANCE = new RegionSectorAllocationBehaviour();

    private RegionSectorAllocationBehaviour() {
    }

    public static RegionSectorAllocationBehaviour getInstance() {
        return INSTANCE;
    }

    public int calculateRequiredSectors(int payloadSizeBytes) {
        return (payloadSizeBytes + 5) / 4096 + 1;
    }

    public boolean exceedsSectorLimit(int requiredSectors) {
        return requiredSectors >= 256;
    }

    public boolean canRewriteInPlace(int sectorOffset, int existingSectorCount, int requiredSectors) {
        return sectorOffset != 0 && existingSectorCount == requiredSectors;
    }
}
