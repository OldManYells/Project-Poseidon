package com.legacyminecraft.poseidon.network;

/**
 * Canonical orchestration for the vehicle/sleeping/ground movement branches of player movement handling.
 */
public final class MovementBranchExecutionSystem {
    private static final MovementBranchExecutionSystem INSTANCE = new MovementBranchExecutionSystem();

    private MovementBranchExecutionSystem() {
    }

    public static MovementBranchExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean execute(MovementBranches movementBranches) {
        if (movementBranches.handleVehicleMovement()) {
            return true;
        }

        if (movementBranches.handleSleepingMovement()) {
            return true;
        }

        PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision =
                movementBranches.resolveGroundMovementDecision();
        movementBranches.logGroundMovementDecision(groundMovementDecision);
        return movementBranches.executeGroundMovementDecision(groundMovementDecision);
    }

    public interface MovementBranches {
        boolean handleVehicleMovement();

        boolean handleSleepingMovement();

        PlayerGroundMovementSystem.GroundMovementDecision resolveGroundMovementDecision();

        void logGroundMovementDecision(PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision);

        boolean executeGroundMovementDecision(PlayerGroundMovementSystem.GroundMovementDecision groundMovementDecision);
    }
}
