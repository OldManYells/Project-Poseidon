package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet13PlayerLookMove;
import org.bukkit.Location;

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
        void sendRollbackPacket(Packet13PlayerLookMove rollbackPacket);

        void teleportPlayer(Location location);
    }
}
