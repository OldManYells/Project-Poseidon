package com.legacyminecraft.poseidon.world;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 * Canonical bootstrap behaviour for region file header/alignment initialization.
 */
public final class RegionFileBootstrapBehaviour {
    private static final RegionFileBootstrapBehaviour INSTANCE = new RegionFileBootstrapBehaviour();

    private RegionFileBootstrapBehaviour() {
    }

    public static RegionFileBootstrapBehaviour getInstance() {
        return INSTANCE;
    }

    public int ensureHeaderTables(RandomAccessFile regionHandle) throws IOException {
        if (regionHandle.length() < 4096L) {
            for (int index = 0; index < 1024; ++index) {
                regionHandle.writeInt(0);
            }

            for (int index = 0; index < 1024; ++index) {
                regionHandle.writeInt(0);
            }

            return 8192;
        }

        return 0;
    }

    public void alignToSectorBoundary(RandomAccessFile regionHandle) throws IOException {
        long remainder = regionHandle.length() & 4095L;
        for (int index = 0; (long) index < remainder; ++index) {
            regionHandle.write(0);
        }
    }

    public ArrayList createSectorUsageMap(int sectorCount) {
        ArrayList sectorUsage = new ArrayList(sectorCount);
        for (int sector = 0; sector < sectorCount; ++sector) {
            sectorUsage.add(Boolean.TRUE);
        }

        sectorUsage.set(0, Boolean.FALSE);
        sectorUsage.set(1, Boolean.FALSE);
        return sectorUsage;
    }
}
