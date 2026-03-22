package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.MineralDropBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class MineralDropServiceTest {
    @Test
    public void oreDropItemCountAndDataRulesMatchLegacyBehavior() {
        MineralDropBehaviour service = MineralDropBehaviour.getInstance();
        Random seeded = new Random(9L);

        Assert.assertEquals(263, service.resolveOreDropItemId(16, 16, 56, 21, 263, 264, 351));
        Assert.assertEquals(264, service.resolveOreDropItemId(56, 16, 56, 21, 263, 264, 351));
        Assert.assertEquals(351, service.resolveOreDropItemId(21, 16, 56, 21, 263, 264, 351));
        Assert.assertEquals(14, service.resolveOreDropItemId(14, 16, 56, 21, 263, 264, 351));

        int lapisCount = service.resolveOreDropCount(21, 21, seeded);
        Assert.assertTrue(lapisCount >= 4);
        Assert.assertTrue(lapisCount <= 8);
        Assert.assertEquals(1, service.resolveOreDropCount(56, 21, seeded));
        Assert.assertEquals(4, service.resolveOreDropData(21, 21));
        Assert.assertEquals(0, service.resolveOreDropData(56, 21));
    }

    @Test
    public void clayAndGlowstoneDropRulesMatchLegacyBehavior() {
        MineralDropBehaviour service = MineralDropBehaviour.getInstance();
        Random seeded = new Random(5L);

        Assert.assertEquals(337, service.resolveClayDropItemId(337));
        Assert.assertEquals(4, service.resolveClayDropCount());
        Assert.assertEquals(348, service.resolveGlowstoneDustDropItemId(348));

        int glowstoneCount = service.resolveGlowstoneDustDropCount(seeded);
        Assert.assertTrue(glowstoneCount >= 2);
        Assert.assertTrue(glowstoneCount <= 4);
    }
}
