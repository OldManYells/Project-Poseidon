package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.FurnaceStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class FurnaceStateServiceTest {
    @Test
    public void dropFacingTextureAndStateRulesMatchLegacyFurnaceBehavior() {
        FurnaceStateBehaviour service = FurnaceStateBehaviour.getInstance();

        Assert.assertEquals(61, service.resolveDropItemId(61));
        Assert.assertEquals(3, service.resolveDefaultFacing(true, false, false, false));
        Assert.assertEquals(2, service.resolveDefaultFacing(false, true, false, false));
        Assert.assertEquals(5, service.resolveDefaultFacing(false, false, true, false));
        Assert.assertEquals(4, service.resolveDefaultFacing(false, false, false, true));
        Assert.assertEquals(62, service.resolveBlockIdForBurningState(true, 62, 61));
        Assert.assertEquals(61, service.resolveBlockIdForBurningState(false, 62, 61));
        Assert.assertEquals(62, service.resolveTextureBySide(1, 45));
        Assert.assertEquals(62, service.resolveTextureBySide(0, 45));
        Assert.assertEquals(44, service.resolveTextureBySide(3, 45));
        Assert.assertEquals(45, service.resolveTextureBySide(2, 45));
        Assert.assertEquals(2, service.resolvePlacementFacingFromYaw(0.0F));
        Assert.assertEquals(5, service.resolvePlacementFacingFromYaw(90.0F));
        Assert.assertEquals(3, service.resolvePlacementFacingFromYaw(180.0F));
        Assert.assertEquals(4, service.resolvePlacementFacingFromYaw(270.0F));
    }

    @Test
    public void randomizedDropHelpersMatchLegacyFurnaceBounds() {
        FurnaceStateBehaviour service = FurnaceStateBehaviour.getInstance();
        Random seeded = new Random(11L);

        float offset = service.resolveDropOffset(seeded);
        Assert.assertTrue(offset >= 0.1F);
        Assert.assertTrue(offset <= 0.9F);

        int chunk = service.resolveDropStackChunk(new Random() {
            @Override
            public int nextInt(int bound) {
                return 20;
            }
        }, 12);
        Assert.assertEquals(12, chunk);

        int smallChunk = service.resolveDropStackChunk(new Random() {
            @Override
            public int nextInt(int bound) {
                return 0;
            }
        }, 64);
        Assert.assertEquals(10, smallChunk);

        double motionX = service.resolveDropHorizontalMotion(seeded, 0.05F);
        double motionY = service.resolveDropVerticalMotion(seeded, 0.05F, 0.2D);
        Assert.assertTrue(motionX > -1.0D && motionX < 1.0D);
        Assert.assertTrue(motionY > -1.0D && motionY < 1.0D);
    }
}
