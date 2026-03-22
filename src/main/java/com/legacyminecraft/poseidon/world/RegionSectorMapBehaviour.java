package com.legacyminecraft.poseidon.world;

import java.util.List;

/**
 * Canonical behaviour for region sector-usage map operations.
 */
public final class RegionSectorMapBehaviour {
    private static final RegionSectorMapBehaviour INSTANCE = new RegionSectorMapBehaviour();

    private RegionSectorMapBehaviour() {
    }

    public static RegionSectorMapBehaviour getInstance() {
        return INSTANCE;
    }

    public void releaseSectors(List sectorUsage, int sectorOffset, int sectorCount) {
        for (int sector = 0; sector < sectorCount; ++sector) {
            sectorUsage.set(sectorOffset + sector, Boolean.TRUE);
        }
    }

    public SectorRun findFreeRun(List sectorUsage, int requiredSectors) {
        int firstFreeSector = sectorUsage.indexOf(Boolean.TRUE);
        int contiguousFree = 0;

        if (firstFreeSector != -1) {
            for (int sector = firstFreeSector; sector < sectorUsage.size(); ++sector) {
                if (contiguousFree != 0) {
                    if (((Boolean) sectorUsage.get(sector)).booleanValue()) {
                        ++contiguousFree;
                    } else {
                        contiguousFree = 0;
                    }
                } else if (((Boolean) sectorUsage.get(sector)).booleanValue()) {
                    firstFreeSector = sector;
                    contiguousFree = 1;
                }

                if (contiguousFree >= requiredSectors) {
                    break;
                }
            }
        }

        return new SectorRun(firstFreeSector, contiguousFree);
    }

    public void markAllocated(List sectorUsage, int sectorOffset, int sectorCount) {
        for (int sector = 0; sector < sectorCount; ++sector) {
            sectorUsage.set(sectorOffset + sector, Boolean.FALSE);
        }
    }

    public void appendAllocated(List sectorUsage, int sectorCount) {
        for (int sector = 0; sector < sectorCount; ++sector) {
            sectorUsage.add(Boolean.FALSE);
        }
    }

    public static final class SectorRun {
        private final int sectorOffset;
        private final int contiguousFreeSectors;

        public SectorRun(int sectorOffset, int contiguousFreeSectors) {
            this.sectorOffset = sectorOffset;
            this.contiguousFreeSectors = contiguousFreeSectors;
        }

        public int getSectorOffset() {
            return sectorOffset;
        }

        public int getContiguousFreeSectors() {
            return contiguousFreeSectors;
        }
    }
}
