package com.legacyminecraft.poseidon.network;

/**
 * Canonical execution flow for processed entity-action packet outcomes.
 */
public final class EntityActionResultExecutionSystem {
    private static final EntityActionResultExecutionSystem INSTANCE = new EntityActionResultExecutionSystem();

    private EntityActionResultExecutionSystem() {
    }

    public static EntityActionResultExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void executeResult(boolean shouldDisableMovementCheck, EntityActionResultActions actions) {
        if (shouldDisableMovementCheck) {
            actions.disableMovementCheck();
        }
    }

    public interface EntityActionResultActions {
        void disableMovementCheck();
    }
}
