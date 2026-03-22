package com.legacyminecraft.poseidon.network;

/**
 * Canonical orchestration for entity-action packet resolution and result execution.
 */
public final class EntityActionPacketExecutionSystem {
    private static final EntityActionPacketExecutionSystem INSTANCE = new EntityActionPacketExecutionSystem();

    private EntityActionPacketExecutionSystem() {
    }

    public static EntityActionPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public void execute(
            EntityActionResultResolver entityActionResultResolver,
            EntityActionResultExecutionSystem entityActionResultExecutionSystem,
            EntityActionResultExecutionSystem.EntityActionResultActions entityActionResultActions
    ) {
        boolean shouldDisableMovementCheck = entityActionResultResolver.resolve();
        entityActionResultExecutionSystem.executeResult(shouldDisableMovementCheck, entityActionResultActions);
    }

    public interface EntityActionResultResolver {
        boolean resolve();
    }
}
