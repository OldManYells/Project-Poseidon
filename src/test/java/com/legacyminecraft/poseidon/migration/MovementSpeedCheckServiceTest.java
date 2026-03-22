package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.MovementSpeedCheckSystem;
import org.junit.Assert;
import org.junit.Test;

public class MovementSpeedCheckServiceTest {
    @Test
    public void noViolationWhenCheckDisabledOrThresholdNotExceeded() {
        MovementSpeedCheckSystem service = MovementSpeedCheckSystem.getInstance();

        MovementSpeedCheckSystem.SpeedCheckDecision disabledDecision =
                service.evaluateWithOptions(false, true, 200.0D, 0.0D, 100.0D, true);
        Assert.assertFalse(disabledDecision.isViolation());

        MovementSpeedCheckSystem.SpeedCheckDecision withinThresholdDecision =
                service.evaluateWithOptions(true, true, 120.0D, 30.0D, 100.0D, true);
        Assert.assertFalse(withinThresholdDecision.isViolation());
    }

    @Test
    public void teleportAndDisconnectOutcomesMatchConfiguredAction() {
        MovementSpeedCheckSystem service = MovementSpeedCheckSystem.getInstance();

        MovementSpeedCheckSystem.SpeedCheckDecision teleportDecision =
                service.evaluateWithOptions(true, true, 300.0D, 0.0D, 100.0D, true);
        Assert.assertTrue(teleportDecision.isViolation());
        Assert.assertTrue(teleportDecision.shouldTeleportBack());
        Assert.assertNull(teleportDecision.getDisconnectReason());

        MovementSpeedCheckSystem.SpeedCheckDecision disconnectDecision =
                service.evaluateWithOptions(true, true, 300.0D, 0.0D, 100.0D, false);
        Assert.assertTrue(disconnectDecision.isViolation());
        Assert.assertFalse(disconnectDecision.shouldTeleportBack());
        Assert.assertEquals("You moved too quickly :( (Hacking?)", disconnectDecision.getDisconnectReason());
    }

    @Test
    public void logMessageIncludesPlayerNameAndDeltas() {
        MovementSpeedCheckSystem service = MovementSpeedCheckSystem.getInstance();
        String message = service.createSpeedViolationLogMessage("Alex", 1.0D, 2.0D, 3.0D);

        Assert.assertTrue(message.contains("Alex"));
        Assert.assertTrue(message.contains("1.0"));
        Assert.assertTrue(message.contains("2.0"));
        Assert.assertTrue(message.contains("3.0"));
    }
}
