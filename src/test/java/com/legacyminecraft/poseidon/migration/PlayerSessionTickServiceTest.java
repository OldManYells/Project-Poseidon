package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.player.PlayerSessionTickSystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerSessionTickServiceTest {
    @Test
    public void portalTriggerWithoutVehicleAdvancesProgressAndTriggersTransferAtThreshold() {
        PlayerSessionTickSystem service = PlayerSessionTickSystem.getInstance();

        PlayerSessionTickSystem.PortalTickDecision nearThresholdDecision =
                service.evaluatePortalTick(true, 0.995F, 20, false, false);

        Assert.assertFalse(nearThresholdDecision.isPortalTriggered());
        Assert.assertEquals(1.0F, nearThresholdDecision.getPortalProgress(), 0.0F);
        Assert.assertEquals(9, nearThresholdDecision.getPortalCooldown());
        Assert.assertTrue(nearThresholdDecision.shouldTriggerWorldTransfer());
        Assert.assertFalse(nearThresholdDecision.shouldCloseContainer());
        Assert.assertFalse(nearThresholdDecision.shouldRemountVehicle());
    }

    @Test
    public void portalTriggerWithVehicleRequestsRemountWithoutProgressIncrement() {
        PlayerSessionTickSystem service = PlayerSessionTickSystem.getInstance();

        PlayerSessionTickSystem.PortalTickDecision decision =
                service.evaluatePortalTick(true, 0.4F, 7, true, true);

        Assert.assertFalse(decision.isPortalTriggered());
        Assert.assertEquals(0.4F, decision.getPortalProgress(), 0.0F);
        Assert.assertEquals(6, decision.getPortalCooldown());
        Assert.assertTrue(decision.shouldCloseContainer());
        Assert.assertTrue(decision.shouldRemountVehicle());
        Assert.assertFalse(decision.shouldTriggerWorldTransfer());
    }

    @Test
    public void portalProgressDecaysAndClampsWhenNotTriggered() {
        PlayerSessionTickSystem service = PlayerSessionTickSystem.getInstance();

        PlayerSessionTickSystem.PortalTickDecision decision =
                service.evaluatePortalTick(false, 0.03F, 0, false, false);

        Assert.assertFalse(decision.isPortalTriggered());
        Assert.assertEquals(0.0F, decision.getPortalProgress(), 0.0F);
        Assert.assertEquals(0, decision.getPortalCooldown());
    }

    @Test
    public void healthSyncOnlySendsWhenHealthChanged() {
        PlayerSessionTickSystem service = PlayerSessionTickSystem.getInstance();

        PlayerSessionTickSystem.HealthSyncDecision unchangedDecision = service.evaluateHealthSync(20, 20);
        Assert.assertFalse(unchangedDecision.shouldSendHealthPacket());
        Assert.assertEquals(20, unchangedDecision.getNextReportedHealth());

        PlayerSessionTickSystem.HealthSyncDecision changedDecision = service.evaluateHealthSync(18, 20);
        Assert.assertTrue(changedDecision.shouldSendHealthPacket());
        Assert.assertEquals(18, changedDecision.getNextReportedHealth());
    }
}
