package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerGroundMovementSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class PlayerGroundMovementServiceTest {
    @Test
    public void disconnectDecisionCarriesReasonAndLogs() {
        List<String> warningLogs = new ArrayList<String>();
        warningLogs.add("warn");
        List<String> consoleLogs = new ArrayList<String>();
        consoleLogs.add("console");

        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.disconnect("disconnect.reason", warningLogs, consoleLogs);

        Assert.assertEquals(PlayerGroundMovementSystem.GroundMovementDecision.Action.DISCONNECT, decision.getAction());
        Assert.assertEquals("disconnect.reason", decision.getDisconnectReason());
        Assert.assertEquals(1, decision.getWarningLogs().size());
        Assert.assertEquals(1, decision.getConsoleLogs().size());
    }

    @Test
    public void teleportDecisionCarriesDestinationRotation() {
        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.teleportLastGood(
                        45.0F,
                        30.0F,
                        new ArrayList<String>(),
                        new ArrayList<String>()
                );

        Assert.assertEquals(PlayerGroundMovementSystem.GroundMovementDecision.Action.TELEPORT_LAST_GOOD, decision.getAction());
        Assert.assertEquals(45.0F, decision.getTeleportYaw(), 0.0F);
        Assert.assertEquals(30.0F, decision.getTeleportPitch(), 0.0F);
    }

    @Test
    public void applyDecisionCarriesGroundAndFallState() {
        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.applyMovement(
                        12,
                        true,
                        0.75D,
                        new ArrayList<String>(),
                        new ArrayList<String>()
                );

        Assert.assertEquals(PlayerGroundMovementSystem.GroundMovementDecision.Action.APPLY_MOVEMENT, decision.getAction());
        Assert.assertEquals(12, decision.getUpdatedFloatingTicks());
        Assert.assertTrue(decision.isOnGround());
        Assert.assertEquals(0.75D, decision.getFallDeltaY(), 0.0D);
    }
}
