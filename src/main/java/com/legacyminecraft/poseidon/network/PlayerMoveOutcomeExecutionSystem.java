package com.legacyminecraft.poseidon.network;


/**
 * Canonical execution flow for resolved player move outcomes.
 */
public final class PlayerMoveOutcomeExecutionSystem {
    private static final PlayerMoveOutcomeExecutionSystem INSTANCE = new PlayerMoveOutcomeExecutionSystem();

    private PlayerMoveOutcomeExecutionSystem() {
    }

    public static PlayerMoveOutcomeExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean executeOutcome(
            PlayerMoveEventOutcomeSystem.MoveOutcomeDecision moveOutcomeDecision,
            MoveOutcomeActions moveOutcomeActions
    ) {
        if (moveOutcomeDecision.shouldSendRollbackPacket()) {
            moveOutcomeActions.sendRollbackPacket(moveOutcomeDecision.getRollbackPacket());
        }
        if (moveOutcomeDecision.shouldTeleportPlayer()) {
            moveOutcomeActions.teleportPlayer(moveOutcomeDecision.getTeleportDestination());
        }
        return moveOutcomeDecision.shouldReturn();
    }

    public interface MoveOutcomeActions {
        void sendRollbackPacket(Object rollbackPacket);

        void teleportPlayer(Object location);
    }
}
