package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical growth, texture, and seed-drop policy for legacy crop wrappers.
 */
public final class CropGrowthBehaviour {
    private static final CropGrowthBehaviour INSTANCE = new CropGrowthBehaviour();

    private CropGrowthBehaviour() {
    }

    public static CropGrowthBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canPlaceOn(int belowTypeId, int farmlandBlockId) {
        return belowTypeId == farmlandBlockId;
    }

    public int matureGrowthStage() {
        return 7;
    }

    public int minGrowthLightLevel() {
        return 9;
    }

    public boolean canAttemptGrowth(int lightLevelAbove, int growthStage) {
        return lightLevelAbove >= minGrowthLightLevel() && growthStage < matureGrowthStage();
    }

    public int resolveNextGrowthStage(int growthStage) {
        return growthStage + 1;
    }

    public boolean shouldIncreaseGrowthStage(Random random, float growthFactor) {
        int bound = (int) (100.0F / growthFactor);
        return random.nextInt(bound) == 0;
    }

    public float computeGrowthFactor(GrowthQuery query, int x, int y, int z, int cropBlockId, int farmlandBlockId) {
        float factor = 1.0F;
        int north = query.getTypeId(x, y, z - 1);
        int south = query.getTypeId(x, y, z + 1);
        int west = query.getTypeId(x - 1, y, z);
        int east = query.getTypeId(x + 1, y, z);
        int northwest = query.getTypeId(x - 1, y, z - 1);
        int northeast = query.getTypeId(x + 1, y, z - 1);
        int southeast = query.getTypeId(x + 1, y, z + 1);
        int southwest = query.getTypeId(x - 1, y, z + 1);
        boolean sideCrops = west == cropBlockId || east == cropBlockId;
        boolean verticalCrops = north == cropBlockId || south == cropBlockId;
        boolean diagonalCrops = northwest == cropBlockId || northeast == cropBlockId || southeast == cropBlockId || southwest == cropBlockId;

        for (int xPos = x - 1; xPos <= x + 1; ++xPos) {
            for (int zPos = z - 1; zPos <= z + 1; ++zPos) {
                int belowId = query.getTypeId(xPos, y - 1, zPos);
                float local = 0.0F;
                if (belowId == farmlandBlockId) {
                    local = 1.0F;
                    if (query.getData(xPos, y - 1, zPos) > 0) {
                        local = 3.0F;
                    }
                }

                if (xPos != x || zPos != z) {
                    local /= 4.0F;
                }

                factor += local;
            }
        }

        if (diagonalCrops || sideCrops && verticalCrops) {
            factor /= 2.0F;
        }

        return factor;
    }

    public int resolveTextureByGrowthStage(int textureId, int growthStage) {
        int stage = growthStage < 0 ? matureGrowthStage() : growthStage;
        return textureId + stage;
    }

    public int seedDropAttempts() {
        return 3;
    }

    public boolean shouldDropSeed(Random random, int growthStage) {
        return random.nextInt(15) <= growthStage;
    }

    public double resolveDropOffset(Random random, float spread) {
        return (double) (random.nextFloat() * spread) + (double) (1.0F - spread) * 0.5D;
    }

    public int resolveDropItemIdByGrowthStage(int growthStage, int matureStage, int wheatItemId, int noDropId) {
        return growthStage == matureStage ? wheatItemId : noDropId;
    }

    public int resolveDropCount() {
        return 1;
    }

    public interface GrowthQuery {
        int getTypeId(int x, int y, int z);

        int getData(int x, int y, int z);
    }
}
