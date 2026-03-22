package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.block.DetectorRailStateBehaviour;
import net.minecraft.server.AxisAlignedBB;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DetectorRailStateServiceTest {
    @Test
    public void powerBitAndSchedulingRulesMatchLegacyDetectorRailBehavior() {
        DetectorRailStateBehaviour service = DetectorRailStateBehaviour.getInstance();

        Assert.assertEquals(20, service.updateDelayTicks());
        Assert.assertFalse(service.isPowered(0));
        Assert.assertTrue(service.isPowered(8));
        Assert.assertEquals(8, service.setPowered(0));
        Assert.assertEquals(0, service.clearPowered(8));
        Assert.assertTrue(service.shouldEvaluateOnEntityCollision(false, 0));
        Assert.assertFalse(service.shouldEvaluateOnEntityCollision(true, 0));
        Assert.assertFalse(service.shouldEvaluateOnEntityCollision(false, 8));
        Assert.assertTrue(service.shouldEvaluateOnTick(false, 8));
        Assert.assertFalse(service.shouldEvaluateOnTick(false, 0));
        Assert.assertTrue(service.isTopSidePowered(8, 1));
        Assert.assertFalse(service.isTopSidePowered(8, 2));
        Assert.assertTrue(service.shouldFireRedstoneTransitionEvent(false, true));
        Assert.assertFalse(service.shouldFireRedstoneTransitionEvent(true, true));
        Assert.assertEquals(1, service.toRedstoneCurrent(true));
        Assert.assertEquals(0, service.toRedstoneCurrent(false));
        Assert.assertTrue(service.shouldPowerOn(false, true));
        Assert.assertTrue(service.shouldPowerOff(true, false));
        Assert.assertTrue(service.shouldScheduleRecheck(true));
        Assert.assertFalse(service.shouldScheduleRecheck(false));
    }

    @Test
    public void detectionBoxAndMinecartPresenceRulesMatchLegacyBehavior() {
        DetectorRailStateBehaviour service = DetectorRailStateBehaviour.getInstance();

        AxisAlignedBB box = service.createDetectionBox(10, 64, 10, 0.125F);
        Assert.assertEquals(10.125D, box.a, 0.0D);
        Assert.assertEquals(64.0D, box.b, 0.0D);
        Assert.assertEquals(10.875D, box.d, 0.0D);
        Assert.assertEquals(64.25D, box.e, 0.0D);

        List entities = new ArrayList();
        Assert.assertFalse(service.hasMinecart(entities));
        entities.add(new Object());
        Assert.assertTrue(service.hasMinecart(entities));
    }
}
