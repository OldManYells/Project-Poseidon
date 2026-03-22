package com.legacyminecraft.poseidon.world;

import java.util.List;

/**
 * Canonical behaviour for RegionFile chunk-write strategy planning and sector-run selection.
 */
public final class RegionChunkWritePlanBehaviour {
    private static final RegionChunkWritePlanBehaviour INSTANCE = new RegionChunkWritePlanBehaviour();

    private RegionChunkWritePlanBehaviour() {
    }

    public static RegionChunkWritePlanBehaviour getInstance() {
        return INSTANCE;
    }

    public WritePlan planWrite(
            int packedLocation,
            int payloadSizeBytes,
            List sectorUsage,
            RegionChunkLocationBehaviour locationBehaviour,
            RegionSectorAllocationBehaviour sectorAllocationBehaviour,
            RegionSectorMapBehaviour sectorMapBehaviour,
            RegionSaveStrategyBehaviour saveStrategyBehaviour
    ) {
        int sectorOffset = locationBehaviour.sectorOffset(packedLocation);
        int sectorCount = locationBehaviour.sectorCount(packedLocation);
        int requiredSectors = sectorAllocationBehaviour.calculateRequiredSectors(payloadSizeBytes);

        if (sectorAllocationBehaviour.exceedsSectorLimit(requiredSectors)) {
            return WritePlan.exceedsLimit(requiredSectors);
        }

        if (sectorAllocationBehaviour.canRewriteInPlace(sectorOffset, sectorCount, requiredSectors)) {
            return WritePlan.rewrite(sectorOffset, requiredSectors);
        }

        sectorMapBehaviour.releaseSectors(sectorUsage, sectorOffset, sectorCount);
        RegionSectorMapBehaviour.SectorRun freeRun = sectorMapBehaviour.findFreeRun(sectorUsage, requiredSectors);
        RegionSaveStrategyBehaviour.SaveStrategy saveStrategy = saveStrategyBehaviour.selectStrategy(
                false,
                freeRun.getContiguousFreeSectors(),
                requiredSectors
        );

        if (saveStrategy == RegionSaveStrategyBehaviour.SaveStrategy.REUSE) {
            return WritePlan.reuse(freeRun.getSectorOffset(), requiredSectors);
        }

        return WritePlan.grow(sectorUsage.size(), requiredSectors);
    }

    public static final class WritePlan {
        private final boolean exceedsSectorLimit;
        private final RegionSaveStrategyBehaviour.SaveStrategy saveStrategy;
        private final int targetSectorOffset;
        private final int requiredSectors;

        private WritePlan(
                boolean exceedsSectorLimit,
                RegionSaveStrategyBehaviour.SaveStrategy saveStrategy,
                int targetSectorOffset,
                int requiredSectors
        ) {
            this.exceedsSectorLimit = exceedsSectorLimit;
            this.saveStrategy = saveStrategy;
            this.targetSectorOffset = targetSectorOffset;
            this.requiredSectors = requiredSectors;
        }

        public static WritePlan exceedsLimit(int requiredSectors) {
            return new WritePlan(true, null, -1, requiredSectors);
        }

        public static WritePlan rewrite(int targetSectorOffset, int requiredSectors) {
            return new WritePlan(false, RegionSaveStrategyBehaviour.SaveStrategy.REWRITE, targetSectorOffset, requiredSectors);
        }

        public static WritePlan reuse(int targetSectorOffset, int requiredSectors) {
            return new WritePlan(false, RegionSaveStrategyBehaviour.SaveStrategy.REUSE, targetSectorOffset, requiredSectors);
        }

        public static WritePlan grow(int targetSectorOffset, int requiredSectors) {
            return new WritePlan(false, RegionSaveStrategyBehaviour.SaveStrategy.GROW, targetSectorOffset, requiredSectors);
        }

        public boolean exceedsSectorLimit() {
            return exceedsSectorLimit;
        }

        public RegionSaveStrategyBehaviour.SaveStrategy getSaveStrategy() {
            return saveStrategy;
        }

        public int getTargetSectorOffset() {
            return targetSectorOffset;
        }

        public int getRequiredSectors() {
            return requiredSectors;
        }
    }
}

