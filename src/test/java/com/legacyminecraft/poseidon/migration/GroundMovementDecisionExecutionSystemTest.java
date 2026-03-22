package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.GroundMovementDecisionExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerGroundMovementSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class GroundMovementDecisionExecutionSystemTest {
    private final GroundMovementDecisionExecutionSystem system = GroundMovementDecisionExecutionSystem.getInstance();

    @Test
    public void executeDecisionDisconnectRoutesToDisconnectAction() {
        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.disconnect(
                        "disconnect-reason",
                        Collections.<String>emptyList(),
                        Collections.<String>emptyList()
                );
        DecisionActionCapture decisionActionCapture = new DecisionActionCapture();

        boolean shouldReturn = system.executeDecision(decision, decisionActionCapture);

        Assert.assertTrue(shouldReturn);
        Assert.assertEquals("disconnect-reason", decisionActionCapture.disconnectReason);
        Assert.assertFalse(decisionActionCapture.teleportCalled);
        Assert.assertFalse(decisionActionCapture.applyCalled);
    }

    @Test
    public void executeDecisionTeleportRoutesToTeleportAction() {
        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.teleportLastGood(
                        12.5F,
                        -5.0F,
                        Collections.<String>emptyList(),
                        Collections.<String>emptyList()
                );
        DecisionActionCapture decisionActionCapture = new DecisionActionCapture();

        boolean shouldReturn = system.executeDecision(decision, decisionActionCapture);

        Assert.assertTrue(shouldReturn);
        Assert.assertTrue(decisionActionCapture.teleportCalled);
        Assert.assertEquals(12.5F, decisionActionCapture.teleportYaw, 0.0001F);
        Assert.assertEquals(-5.0F, decisionActionCapture.teleportPitch, 0.0001F);
        Assert.assertFalse(decisionActionCapture.applyCalled);
    }

    @Test
    public void executeDecisionAbortReturnsWithoutActions() {
        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.abort(
                        Collections.<String>emptyList(),
                        Collections.<String>emptyList()
                );
        DecisionActionCapture decisionActionCapture = new DecisionActionCapture();

        boolean shouldReturn = system.executeDecision(decision, decisionActionCapture);

        Assert.assertTrue(shouldReturn);
        Assert.assertNull(decisionActionCapture.disconnectReason);
        Assert.assertFalse(decisionActionCapture.teleportCalled);
        Assert.assertFalse(decisionActionCapture.applyCalled);
    }

    @Test
    public void executeDecisionApplyMovementRoutesToApplyAction() {
        PlayerGroundMovementSystem.GroundMovementDecision decision =
                PlayerGroundMovementSystem.GroundMovementDecision.applyMovement(
                        23,
                        true,
                        -0.75D,
                        Collections.<String>emptyList(),
                        Collections.<String>emptyList()
                );
        DecisionActionCapture decisionActionCapture = new DecisionActionCapture();

        boolean shouldReturn = system.executeDecision(decision, decisionActionCapture);

        Assert.assertFalse(shouldReturn);
        Assert.assertTrue(decisionActionCapture.applyCalled);
        Assert.assertEquals(23, decisionActionCapture.floatingTicks);
        Assert.assertTrue(decisionActionCapture.onGround);
        Assert.assertEquals(-0.75D, decisionActionCapture.fallDeltaY, 0.0001D);
    }

    private static final class DecisionActionCapture implements GroundMovementDecisionExecutionSystem.DecisionActions {
        private String disconnectReason;
        private boolean teleportCalled;
        private float teleportYaw;
        private float teleportPitch;
        private boolean applyCalled;
        private int floatingTicks;
        private boolean onGround;
        private double fallDeltaY;

        @Override
        public void disconnect(String reason) {
            this.disconnectReason = reason;
        }

        @Override
        public void teleportToLastGood(float yaw, float pitch) {
            this.teleportCalled = true;
            this.teleportYaw = yaw;
            this.teleportPitch = pitch;
        }

        @Override
        public void applyMovement(int floatingTicks, boolean onGround, double fallDeltaY) {
            this.applyCalled = true;
            this.floatingTicks = floatingTicks;
            this.onGround = onGround;
            this.fallDeltaY = fallDeltaY;
        }
    }
}
