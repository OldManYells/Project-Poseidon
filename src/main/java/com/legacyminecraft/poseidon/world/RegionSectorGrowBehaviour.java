package com.legacyminecraft.poseidon.world;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Canonical behaviour for region sector growth writes/stat accounting.
 */
public final class RegionSectorGrowBehaviour {
    private static final RegionSectorGrowBehaviour INSTANCE = new RegionSectorGrowBehaviour();

    private RegionSectorGrowBehaviour() {
    }

    public static RegionSectorGrowBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeEmptySectors(RandomAccessFile regionHandle, byte[] sectorBytes, int sectorCount) throws IOException {
        for (int sector = 0; sector < sectorCount; ++sector) {
            regionHandle.write(sectorBytes);
        }
    }

    public int bytesAddedByGrow(int sectorCount) {
        return 4096 * sectorCount;
    }
}
