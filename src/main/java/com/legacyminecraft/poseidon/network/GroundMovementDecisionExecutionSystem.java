package com.legacyminecraft.poseidon.network;

/**
 * Canonical action execution flow for processed ground-movement decisions.
 */
public final class GroundMovementDecisionExecutionSystem {
    private static final GroundMovementDecisionExecutionSystem INSTANCE = new GroundMovementDecisionExecutionSystem();

    private GroundMovementDecisionExecutionSystem() {
    }

    public static GroundMovementDecisionExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean executeDecision(
            PlayerGroundMovementSystem.GroundMovementDecision decision,
            DecisionActions decisionActions
    ) {
        if (decision.getAction() == PlayerGroundMovementSystem.GroundMovementDecision.Action.DISCONNECT) {
            decisionActions.disconnect(decision.getDisconnectReason());
            return true;
        }

        if (decision.getAction() == PlayerGroundMovementSystem.GroundMovementDecision.Action.TELEPORT_LAST_GOOD) {
            decisionActions.teleportToLastGood(decision.getTeleportYaw(), decision.getTeleportPitch());
            return true;
        }

        if (decision.getAction() == PlayerGroundMovementSystem.GroundMovementDecision.Action.ABORT) {
            return true;
        }

        decisionActions.applyMovement(
                decision.getUpdatedFloatingTicks(),
                decision.isOnGround(),
                decision.getFallDeltaY()
        );
        return false;
    }

    public interface DecisionActions {
        void disconnect(String reason);

        void teleportToLastGood(float yaw, float pitch);

        void applyMovement(int floatingTicks, boolean onGround, double fallDeltaY);
    }
}
