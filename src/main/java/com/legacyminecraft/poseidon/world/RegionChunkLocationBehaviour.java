package com.legacyminecraft.poseidon.world;

import java.util.List;

/**
 * Canonical codec behaviour for packed region chunk location values.
 */
public final class RegionChunkLocationBehaviour {
    private static final RegionChunkLocationBehaviour INSTANCE = new RegionChunkLocationBehaviour();

    private RegionChunkLocationBehaviour() {
    }

    public static RegionChunkLocationBehaviour getInstance() {
        return INSTANCE;
    }

    public int sectorOffset(int packedLocation) {
        return packedLocation >> 8;
    }

    public int sectorCount(int packedLocation) {
        return packedLocation & 255;
    }

    public int compose(int sectorOffset, int sectorCount) {
        return sectorOffset << 8 | sectorCount;
    }

    public boolean isValidSectorRange(int sectorOffset, int sectorCount, int totalSectors) {
        return packedLocationPresent(sectorOffset, sectorCount) && sectorOffset + sectorCount <= totalSectors;
    }

    public boolean packedLocationPresent(int sectorOffset, int sectorCount) {
        return sectorOffset != 0 || sectorCount != 0;
    }

    public void markAllocatedSectorsForLocation(List sectorUsage, int packedLocation) {
        int sectorOffset = sectorOffset(packedLocation);
        int sectorCount = sectorCount(packedLocation);
        if (!isValidSectorRange(sectorOffset, sectorCount, sectorUsage.size())) {
            return;
        }

        for (int sector = 0; sector < sectorCount; ++sector) {
            sectorUsage.set(sectorOffset + sector, Boolean.FALSE);
        }
    }
}
