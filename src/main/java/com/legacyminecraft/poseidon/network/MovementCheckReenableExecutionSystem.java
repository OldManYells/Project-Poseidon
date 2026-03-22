package com.legacyminecraft.poseidon.network;

/**
 * Canonical execution for movement-check re-enable decisions in player movement packet flow.
 */
public final class MovementCheckReenableExecutionSystem {
    private static final MovementCheckReenableExecutionSystem INSTANCE = new MovementCheckReenableExecutionSystem();

    private MovementCheckReenableExecutionSystem() {
    }

    public static MovementCheckReenableExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean apply(
            boolean checkMovementEnabled,
            double packetX,
            double packetY,
            double packetZ,
            double anchorX,
            double anchorY,
            double anchorZ,
            MovementPacketPolicy movementPacketPolicy
    ) {
        if (movementPacketPolicy.shouldReEnableMovementCheck(
                checkMovementEnabled,
                packetX,
                packetY,
                packetZ,
                anchorX,
                anchorY,
                anchorZ
        )) {
            return true;
        }
        return checkMovementEnabled;
    }
}
