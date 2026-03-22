package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.network.PlayerMoveEventDispatchSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventExecutionSystem;
import com.legacyminecraft.poseidon.network.PlayerMoveEventOutcomeSystem;
import org.junit.Assert;
import org.junit.Test;

public class PlayerMoveEventExecutionSystemTest {
    private final PlayerMoveEventExecutionSystem playerMoveEventExecutionSystem =
            PlayerMoveEventExecutionSystem.getInstance();

    @Test
    public void executeRunsFlowInOrderAndReturnsOutcomeExecutionResult() {
        FlowCapture flowCapture = new FlowCapture(true);

        boolean result = playerMoveEventExecutionSystem.execute(flowCapture);

        Assert.assertTrue(result);
        Assert.assertEquals(1, flowCapture.createCalls);
        Assert.assertEquals(1, flowCapture.dispatchCalls);
        Assert.assertEquals(1, flowCapture.applyStateCalls);
        Assert.assertEquals(1, flowCapture.resolveCalls);
        Assert.assertEquals(1, flowCapture.executeOutcomeCalls);
        Assert.assertEquals("create>dispatch>apply>resolve>execute>", flowCapture.callOrder.toString());
    }

    @Test
    public void executeReturnsFalseWhenOutcomeExecutionDeclinesContinuation() {
        FlowCapture flowCapture = new FlowCapture(false);

        boolean result = playerMoveEventExecutionSystem.execute(flowCapture);

        Assert.assertFalse(result);
        Assert.assertEquals(1, flowCapture.executeOutcomeCalls);
    }

    private static final class FlowCapture implements PlayerMoveEventExecutionSystem.MoveEventFlow {
        private final boolean executeOutcomeResult;
        private final StringBuilder callOrder = new StringBuilder();
        private int createCalls;
        private int dispatchCalls;
        private int applyStateCalls;
        private int resolveCalls;
        private int executeOutcomeCalls;
        private PlayerMoveEventDispatchSystem.MovementEventState createdState;
        private PlayerMoveEventDispatchSystem.MoveEventResult dispatchedResult;
        private PlayerMoveEventOutcomeSystem.MoveOutcomeDecision resolvedDecision;

        private FlowCapture(boolean executeOutcomeResult) {
            this.executeOutcomeResult = executeOutcomeResult;
        }

        @Override
        public PlayerMoveEventDispatchSystem.MovementEventState createMovementEventState() {
            this.createCalls++;
            this.callOrder.append("create>");
            this.createdState = new PlayerMoveEventDispatchSystem.MovementEventState(1.0D, 2.0D, 3.0D, 4.0F, 5.0F, false);
            return this.createdState;
        }

        @Override
        public PlayerMoveEventDispatchSystem.MoveEventResult dispatchMoveEvent(
                PlayerMoveEventDispatchSystem.MovementEventState movementEventState
        ) {
            this.dispatchCalls++;
            this.callOrder.append("dispatch>");
            Assert.assertSame(this.createdState, movementEventState);
            this.dispatchedResult = PlayerMoveEventDispatchSystem.MoveEventResult.continueProcessing();
            return this.dispatchedResult;
        }

        @Override
        public void applyMovementState(PlayerMoveEventDispatchSystem.MovementEventState movementEventState) {
            this.applyStateCalls++;
            this.callOrder.append("apply>");
            Assert.assertSame(this.createdState, movementEventState);
        }

        @Override
        public PlayerMoveEventOutcomeSystem.MoveOutcomeDecision resolveMoveOutcome(
                PlayerMoveEventDispatchSystem.MoveEventResult moveEventResult
        ) {
            this.resolveCalls++;
            this.callOrder.append("resolve>");
            Assert.assertSame(this.dispatchedResult, moveEventResult);
            this.resolvedDecision = PlayerMoveEventOutcomeSystem.MoveOutcomeDecision.continueProcessing();
            return this.resolvedDecision;
        }

        @Override
        public boolean executeMoveOutcome(PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision) {
            this.executeOutcomeCalls++;
            this.callOrder.append("execute>");
            Assert.assertSame(this.resolvedDecision, moveOutcomeDecision);
            return this.executeOutcomeResult;
        }
    }
}
