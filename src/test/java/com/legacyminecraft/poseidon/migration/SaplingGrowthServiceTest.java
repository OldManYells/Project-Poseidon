package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.SaplingGrowthBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class SaplingGrowthServiceTest {
    @Test
    public void growthMarkAndTextureRulesMatchLegacyBehavior() {
        SaplingGrowthBehaviour service = SaplingGrowthBehaviour.getInstance();
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Assert.assertEquals(8, service.growthMarkBit());
        Assert.assertEquals(2, service.extractVariant(10));
        Assert.assertTrue(service.shouldAttemptGrowth(9, deterministic));
        Assert.assertFalse(service.shouldAttemptGrowth(8, deterministic));
        Assert.assertTrue(service.isMarkedForGrowth(8));
        Assert.assertFalse(service.isMarkedForGrowth(0));
        Assert.assertEquals(9, service.markForGrowth(1));
        Assert.assertEquals(63, service.resolveTextureByVariant(1, 15));
        Assert.assertEquals(79, service.resolveTextureByVariant(2, 15));
        Assert.assertEquals(15, service.resolveTextureByVariant(0, 15));
    }

    @Test
    public void generatorSelectionMatchesLegacyVariantAndChanceRules() {
        SaplingGrowthBehaviour service = SaplingGrowthBehaviour.getInstance();
        Random deterministic = new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        };

        Assert.assertEquals(SaplingGrowthBehaviour.TreeGeneratorType.TAIGA, service.resolveGeneratorType(1, deterministic));
        Assert.assertEquals(SaplingGrowthBehaviour.TreeGeneratorType.FOREST, service.resolveGeneratorType(2, deterministic));
        Assert.assertEquals(SaplingGrowthBehaviour.TreeGeneratorType.BIG_TREE, service.resolveGeneratorType(0, deterministic));
        Assert.assertEquals(SaplingGrowthBehaviour.TreeGeneratorType.BIG_TREE, service.resolveGeneratorType(3, deterministic));
    }
}
