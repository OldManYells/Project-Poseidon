package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.CropGrowthBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class CropGrowthServiceTest {
    @Test
    public void placementAndGrowthStageRulesMatchLegacyBehavior() {
        CropGrowthBehaviour service = CropGrowthBehaviour.getInstance();
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Assert.assertTrue(service.canPlaceOn(60, 60));
        Assert.assertFalse(service.canPlaceOn(3, 60));
        Assert.assertEquals(7, service.matureGrowthStage());
        Assert.assertEquals(9, service.minGrowthLightLevel());
        Assert.assertTrue(service.canAttemptGrowth(9, 0));
        Assert.assertFalse(service.canAttemptGrowth(8, 0));
        Assert.assertFalse(service.canAttemptGrowth(15, 7));
        Assert.assertEquals(3, service.resolveNextGrowthStage(2));
        Assert.assertTrue(service.shouldIncreaseGrowthStage(deterministic, 2.0F));
    }

    @Test
    public void growthFactorTextureAndDropRulesMatchLegacyBehavior() {
        CropGrowthBehaviour service = CropGrowthBehaviour.getInstance();

        float factor = service.computeGrowthFactor(new CropGrowthBehaviour.GrowthQuery() {
            public int getTypeId(int x, int y, int z) {
                if (y == 9 && x == 5 && z == 5) {
                    return 60;
                }
                return 0;
            }

            public int getData(int x, int y, int z) {
                if (y == 9 && x == 5 && z == 5) {
                    return 7;
                }
                return 0;
            }
        }, 5, 10, 5, 59, 60);
        Assert.assertEquals(4.0F, factor, 0.0F);

        Assert.assertEquals(95, service.resolveTextureByGrowthStage(88, 7));
        Assert.assertEquals(95, service.resolveTextureByGrowthStage(88, -1));
        Assert.assertEquals(3, service.seedDropAttempts());
        Assert.assertEquals(296, service.resolveDropItemIdByGrowthStage(7, 7, 296, -1));
        Assert.assertEquals(-1, service.resolveDropItemIdByGrowthStage(6, 7, 296, -1));
        Assert.assertEquals(1, service.resolveDropCount());

        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };
        Assert.assertTrue(service.shouldDropSeed(deterministic, 0));
        double offset = service.resolveDropOffset(new Random(7L), 0.7F);
        Assert.assertTrue(offset >= 0.15D);
        Assert.assertTrue(offset < 0.85D);
    }
}
