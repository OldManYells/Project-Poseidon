package com.legacyminecraft.poseidon.network;

/**
 * Canonical orchestration for move-event dispatch, state-apply, and outcome execution.
 */
public final class PlayerMoveEventExecutionSystem {
    private static final PlayerMoveEventExecutionSystem INSTANCE = new PlayerMoveEventExecutionSystem();

    private PlayerMoveEventExecutionSystem() {
    }

    public static PlayerMoveEventExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean execute(MoveEventFlow moveEventFlow) {
        PlayerMoveEventDispatchSystem.MovementEventState movementEventState = moveEventFlow.createMovementEventState();
        PlayerMoveEventDispatchSystem.MoveEventResult moveEventResult = moveEventFlow.dispatchMoveEvent(movementEventState);
        moveEventFlow.applyMovementState(movementEventState);
        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision =
                moveEventFlow.resolveMoveOutcome(moveEventResult);
        return moveEventFlow.executeMoveOutcome(moveOutcomeDecision);
    }

    public interface MoveEventFlow {
        PlayerMoveEventDispatchSystem.MovementEventState createMovementEventState();

        PlayerMoveEventDispatchSystem.MoveEventResult dispatchMoveEvent(
                PlayerMoveEventDispatchSystem.MovementEventState movementEventState
        );

        void applyMovementState(PlayerMoveEventDispatchSystem.MovementEventState movementEventState);

        PlayerMoveEventOutcomeSystem.MoveOutcomeDecision resolveMoveOutcome(
                PlayerMoveEventDispatchSystem.MoveEventResult moveEventResult
        );

        boolean executeMoveOutcome(PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision);
    }
}
