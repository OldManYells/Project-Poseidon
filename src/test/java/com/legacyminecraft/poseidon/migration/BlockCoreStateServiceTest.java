package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.BlockCoreStateBehaviour;
import org.junit.Assert;
import org.junit.Test;

public class BlockCoreStateServiceTest {
    @Test
    public void damageDropAndPlacementRulesMatchLegacyBehavior() {
        BlockCoreStateBehaviour service = BlockCoreStateBehaviour.getInstance();

        Assert.assertTrue(service.isNeighborBuildable(true));
        Assert.assertFalse(service.isNeighborBuildable(false));
        Assert.assertEquals(0.0F, service.resolveDamageProgress(-1.0F, true, 1.0F), 0.0F);
        Assert.assertEquals(1.0F / 1.0F / 100.0F, service.resolveDamageProgress(1.0F, false, 1.0F), 0.0F);
        Assert.assertEquals(2.0F / 2.0F / 30.0F, service.resolveDamageProgress(2.0F, true, 2.0F), 0.0F);
        Assert.assertTrue(service.shouldProcessDrops(false));
        Assert.assertFalse(service.shouldProcessDrops(true));
        Assert.assertTrue(service.shouldDropItem(0.4F, 0.5F));
        Assert.assertFalse(service.shouldDropItem(0.6F, 0.5F));
        Assert.assertEquals(0.15000000596046448D, service.resolveDropOffset(0.0F, 0.7F), 0.0D);
        Assert.assertEquals(10, service.resolvePickupDelay());
        Assert.assertTrue(service.canReplace(0, false));
        Assert.assertTrue(service.canReplace(1, true));
        Assert.assertFalse(service.canReplace(1, false));
    }

    @Test
    public void axisBoundsChecksMatchLegacyBehavior() {
        BlockCoreStateBehaviour service = BlockCoreStateBehaviour.getInstance();

        Assert.assertTrue(service.isWithinYZ(0.5D, 0.5D, 0.0D, 1.0D, 0.0D, 1.0D));
        Assert.assertFalse(service.isWithinYZ(1.5D, 0.5D, 0.0D, 1.0D, 0.0D, 1.0D));
        Assert.assertTrue(service.isWithinXZ(0.5D, 0.5D, 0.0D, 1.0D, 0.0D, 1.0D));
        Assert.assertFalse(service.isWithinXZ(-0.1D, 0.5D, 0.0D, 1.0D, 0.0D, 1.0D));
        Assert.assertTrue(service.isWithinXY(0.5D, 0.5D, 0.0D, 1.0D, 0.0D, 1.0D));
        Assert.assertFalse(service.isWithinXY(0.5D, 1.2D, 0.0D, 1.0D, 0.0D, 1.0D));
    }
}
