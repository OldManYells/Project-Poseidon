package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet13PlayerLookMove;

/**
 * Canonical execution flow for applying prepared teleport plans.
 */
public final class PlayerTeleportPlanApplySystem {
    private static final PlayerTeleportPlanApplySystem INSTANCE = new PlayerTeleportPlanApplySystem();

    private PlayerTeleportPlanApplySystem() {
    }

    public static PlayerTeleportPlanApplySystem getInstance() {
        return INSTANCE;
    }

    public void applyPlan(
            PlayerTeleportExecutionSystem.TeleportExecutionPlan teleportExecutionPlan,
            TeleportPlanActions teleportPlanActions
    ) {
        teleportPlanActions.applyMovementState(
                teleportExecutionPlan.getX(),
                teleportExecutionPlan.getY(),
                teleportExecutionPlan.getZ(),
                teleportExecutionPlan.getYaw(),
                teleportExecutionPlan.getPitch(),
                teleportExecutionPlan.isJustTeleported(),
                teleportExecutionPlan.isMovementCheckEnabled()
        );
        teleportPlanActions.applyPlayerLocation(
                teleportExecutionPlan.getX(),
                teleportExecutionPlan.getY(),
                teleportExecutionPlan.getZ(),
                teleportExecutionPlan.getYaw(),
                teleportExecutionPlan.getPitch()
        );
        teleportPlanActions.sendTeleportPacket(teleportExecutionPlan.getTeleportPacket());
    }

    public interface TeleportPlanActions {
        void applyMovementState(
                double x,
                double y,
                double z,
                float yaw,
                float pitch,
                boolean justTeleported,
                boolean movementCheckEnabled
        );

        void applyPlayerLocation(double x, double y, double z, float yaw, float pitch);

        void sendTeleportPacket(Packet13PlayerLookMove teleportPacket);
    }
}
