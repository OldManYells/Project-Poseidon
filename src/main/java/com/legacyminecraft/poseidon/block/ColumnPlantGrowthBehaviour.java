package com.legacyminecraft.poseidon.block;

/**
 * Canonical vertical-column growth policy for legacy plant wrappers.
 */
public final class ColumnPlantGrowthBehaviour {
    private static final ColumnPlantGrowthBehaviour INSTANCE = new ColumnPlantGrowthBehaviour();

    private ColumnPlantGrowthBehaviour() {
    }

    public static ColumnPlantGrowthBehaviour getInstance() {
        return INSTANCE;
    }

    public int countContiguousBelow(BlockIdQuery query, int x, int y, int z, int blockId) {
        int count = 1;
        while (query.getTypeId(x, y - count, z) == blockId) {
            ++count;
        }
        return count;
    }

    public boolean shouldAttemptGrowth(boolean isAboveEmpty, int columnHeight, int maxHeight) {
        return isAboveEmpty && columnHeight < maxHeight;
    }

    public boolean shouldSpawnNewSegment(int growthData, int maxGrowthData) {
        return growthData == maxGrowthData;
    }

    public int nextGrowthData(int growthData, int maxGrowthData) {
        return growthData >= maxGrowthData ? 0 : growthData + 1;
    }

    public interface BlockIdQuery {
        int getTypeId(int x, int y, int z);
    }
}
