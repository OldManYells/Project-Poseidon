package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.MovementBranchExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerGroundMovementSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collections;

public class MovementBranchExecutionSystemTest {
    private final MovementBranchExecutionSystem movementBranchExecutionSystem = MovementBranchExecutionSystem.getInstance();

    @Test
    public void executeReturnsImmediatelyWhenVehicleBranchHandled() {
        BranchesCapture branchesCapture = new BranchesCapture();
        branchesCapture.vehicleHandled = true;

        boolean handled = movementBranchExecutionSystem.execute(branchesCapture);

        Assert.assertTrue(handled);
        Assert.assertEquals(1, branchesCapture.vehicleCalls);
        Assert.assertEquals(0, branchesCapture.sleepingCalls);
        Assert.assertEquals(0, branchesCapture.resolveCalls);
    }

    @Test
    public void executeReturnsImmediatelyWhenSleepingBranchHandled() {
        BranchesCapture branchesCapture = new BranchesCapture();
        branchesCapture.sleepingHandled = true;

        boolean handled = movementBranchExecutionSystem.execute(branchesCapture);

        Assert.assertTrue(handled);
        Assert.assertEquals(1, branchesCapture.vehicleCalls);
        Assert.assertEquals(1, branchesCapture.sleepingCalls);
        Assert.assertEquals(0, branchesCapture.resolveCalls);
    }

    @Test
    public void executeResolvesLogsAndExecutesGroundDecisionWhenNoEarlyBranchHandled() {
        BranchesCapture branchesCapture = new BranchesCapture();
        branchesCapture.groundDecisionHandled = false;

        boolean handled = movementBranchExecutionSystem.execute(branchesCapture);

        Assert.assertFalse(handled);
        Assert.assertEquals(1, branchesCapture.vehicleCalls);
        Assert.assertEquals(1, branchesCapture.sleepingCalls);
        Assert.assertEquals(1, branchesCapture.resolveCalls);
        Assert.assertEquals(1, branchesCapture.logCalls);
        Assert.assertEquals(1, branchesCapture.executeCalls);
    }

    private static final class BranchesCapture implements MovementBranchExecutionSystem.MovementBranches {
        private boolean vehicleHandled;
        private boolean sleepingHandled;
        private boolean groundDecisionHandled;
        private int vehicleCalls;
        private int sleepingCalls;
        private int resolveCalls;
        private int logCalls;
        private int executeCalls;
        private final PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision =
                PlayerGroundMovementSystem.GroundMovementDecision.abort(
                        Collections.<String>emptyList(),
                        Collections.<String>emptyList()
                );

        @Override
        public boolean handleVehicleMovement() {
            this.vehicleCalls++;
            return this.vehicleHandled;
        }

        @Override
        public boolean handleSleepingMovement() {
            this.sleepingCalls++;
            return this.sleepingHandled;
        }

        @Override
        public PlayerGroundMovementSystem.GroundMovementDecision resolveGroundMovementDecision() {
            this.resolveCalls++;
            return this.groundMovementDecision;
        }

        @Override
        public void logGroundMovementDecision(PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision) {
            this.logCalls++;
        }

        @Override
        public boolean executeGroundMovementDecision(PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision) {
            this.executeCalls++;
            return this.groundDecisionHandled;
        }
    }
}
