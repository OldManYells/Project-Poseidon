package com.legacyminecraft.poseidon.network;

/**
 * Canonical execution flow for applying mutable movement-event state snapshots.
 */
public final class PlayerMoveEventStateApplySystem {
    private static final PlayerMoveEventStateApplySystem INSTANCE = new PlayerMoveEventStateApplySystem();

    private PlayerMoveEventStateApplySystem() {
    }

    public static PlayerMoveEventStateApplySystem getInstance() {
        return INSTANCE;
    }

    public void applyState(
            PlayerMoveEventDispatchSystem.MovementEventState movementEventState,
            MovementStateSink movementStateSink
    ) {
        movementStateSink.apply(
                movementEventState.getLastPosX(),
                movementEventState.getLastPosY(),
                movementEventState.getLastPosZ(),
                movementEventState.getLastYaw(),
                movementEventState.getLastPitch(),
                movementEventState.isJustTeleported()
        );
    }

    public interface MovementStateSink {
        void apply(double lastPosX, double lastPosY, double lastPosZ, float lastYaw, float lastPitch, boolean justTeleported);
    }
}
