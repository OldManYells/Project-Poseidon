package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.Packet10Flying;
import net.minecraft.server.WorldServer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Canonical orchestration for ground movement continuation after PlayerMoveEvent handling.
 */
public final class PlayerGroundMovementSystem {
    private static final PlayerGroundMovementSystem INSTANCE = new PlayerGroundMovementSystem();

    private final GroundMovementResolutionSystem groundMovementResolutionSystem = GroundMovementResolutionSystem.getInstance();
    private final MovementSpeedCheckSystem movementSpeedCheckSystem = MovementSpeedCheckSystem.getInstance();
    private final PlayerMovementPacketPreparationSystem playerMovementPacketPreparationSystem = PlayerMovementPacketPreparationSystem.getInstance();

    private PlayerGroundMovementSystem() {
    }

    public static PlayerGroundMovementSystem getInstance() {
        return INSTANCE;
    }

    public GroundMovementDecision processGroundMovement(
            EntityPlayer player,
            WorldServer worldserver,
            Packet10Flying packet10flying,
            double anchorX,
            double anchorY,
            double anchorZ,
            boolean checkMovement,
            boolean allowFlight,
            int floatingTicks
    ) {
        List<String> warningLogs = new ArrayList<String>();
        List<String> consoleLogs = new ArrayList<String>();
        double previousY = player.locY;

        PlayerMovementPacketPreparationSystem.MovementPreparationResult movementPreparationResult =
                playerMovementPacketPreparationSystem.prepareForPlayer(player, packet10flying);
        if (!movementPreparationResult.isValid()) {
            if (movementPreparationResult.isIllegalStance()) {
                warningLogs.add(player.name + " had an illegal stance: " + movementPreparationResult.getIllegalStanceDelta());
            }
            return GroundMovementDecision.disconnect(
                    movementPreparationResult.getDisconnectReason(),
                    warningLogs,
                    consoleLogs
            );
        }

        double targetX = movementPreparationResult.getTargetX();
        double targetY = movementPreparationResult.getTargetY();
        double targetZ = movementPreparationResult.getTargetZ();
        float targetYaw = movementPreparationResult.getTargetYaw();
        float targetPitch = movementPreparationResult.getTargetPitch();

        player.a(true);
        player.br = 0.0F;
        player.setLocation(anchorX, anchorY, anchorZ, targetYaw, targetPitch);
        if (!checkMovement) {
            return GroundMovementDecision.abort(warningLogs, consoleLogs);
        }

        double deltaX = targetX - player.locX;
        double deltaY = targetY - player.locY;
        double deltaZ = targetZ - player.locZ;
        double velocitySquared =
                player.motX * player.motX + player.motY * player.motY + player.motZ * player.motZ;
        double movementSquared = deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ;

        MovementSpeedCheckSystem.SpeedCheckDecision speedCheckDecision =
                movementSpeedCheckSystem.evaluateConfiguredDecision(checkMovement, movementSquared, velocitySquared);
        if (speedCheckDecision.isViolation()) {
            warningLogs.add(movementSpeedCheckSystem.createSpeedViolationLogMessage(player.name, deltaX, deltaY, deltaZ));
            if (speedCheckDecision.shouldTeleportBack()) {
                return GroundMovementDecision.teleportLastGood(targetYaw, targetPitch, warningLogs, consoleLogs);
            }
            return GroundMovementDecision.disconnect(speedCheckDecision.getDisconnectReason(), warningLogs, consoleLogs);
        }

        GroundMovementResolutionSystem.GroundMovementResult groundMovementResult =
                groundMovementResolutionSystem.resolveGroundMovement(
                        player,
                        worldserver,
                        targetX,
                        targetY,
                        targetZ,
                        targetYaw,
                        targetPitch,
                        deltaX,
                        deltaY,
                        deltaZ,
                        allowFlight,
                        floatingTicks
                );

        if (groundMovementResult.isMovedWrongly()) {
            warningLogs.add(groundMovementResolutionSystem.createMovedWronglyWarningMessage(player.name));
            consoleLogs.add(groundMovementResolutionSystem.createMovedWronglyReceivedPosition(targetX, targetY, targetZ));
            consoleLogs.add(
                    groundMovementResolutionSystem.createMovedWronglyExpectedPosition(
                            groundMovementResult.getExpectedX(),
                            groundMovementResult.getExpectedY(),
                            groundMovementResult.getExpectedZ()
                    )
            );
        }

        if (groundMovementResult.shouldTeleportToLastGoodPosition()) {
            return GroundMovementDecision.teleportLastGood(targetYaw, targetPitch, warningLogs, consoleLogs);
        }

        int updatedFloatingTicks = groundMovementResult.getUpdatedFloatingTicks();
        if (groundMovementResult.shouldKickForFloating()) {
            warningLogs.add(groundMovementResolutionSystem.createFloatingKickLogMessage(player.name));
            return GroundMovementDecision.disconnect(
                    groundMovementResult.getFloatingDisconnectReason(),
                    warningLogs,
                    consoleLogs
            );
        }

        return GroundMovementDecision.applyMovement(
                updatedFloatingTicks,
                packet10flying.g,
                player.locY - previousY,
                warningLogs,
                consoleLogs
        );
    }

    public static final class GroundMovementDecision {
        public enum Action {
            ABORT,
            TELEPORT_LAST_GOOD,
            DISCONNECT,
            APPLY_MOVEMENT
        }

        private final Action action;
        private final String disconnectReason;
        private final float teleportYaw;
        private final float teleportPitch;
        private final int updatedFloatingTicks;
        private final boolean onGround;
        private final double fallDeltaY;
        private final List<String> warningLogs;
        private final List<String> consoleLogs;

        private GroundMovementDecision(
                Action action,
                String disconnectReason,
                float teleportYaw,
                float teleportPitch,
                int updatedFloatingTicks,
                boolean onGround,
                double fallDeltaY,
                List<String> warningLogs,
                List<String> consoleLogs
        ) {
            this.action = action;
            this.disconnectReason = disconnectReason;
            this.teleportYaw = teleportYaw;
            this.teleportPitch = teleportPitch;
            this.updatedFloatingTicks = updatedFloatingTicks;
            this.onGround = onGround;
            this.fallDeltaY = fallDeltaY;
            this.warningLogs = Collections.unmodifiableList(new ArrayList<String>(warningLogs));
            this.consoleLogs = Collections.unmodifiableList(new ArrayList<String>(consoleLogs));
        }

        public static GroundMovementDecision abort(List<String> warningLogs, List<String> consoleLogs) {
            return new GroundMovementDecision(Action.ABORT, null, 0.0F, 0.0F, 0, false, 0.0D, warningLogs, consoleLogs);
        }

        public static GroundMovementDecision teleportLastGood(
                float teleportYaw,
                float teleportPitch,
                List<String> warningLogs,
                List<String> consoleLogs
        ) {
            return new GroundMovementDecision(
                    Action.TELEPORT_LAST_GOOD,
                    null,
                    teleportYaw,
                    teleportPitch,
                    0,
                    false,
                    0.0D,
                    warningLogs,
                    consoleLogs
            );
        }

        public static GroundMovementDecision disconnect(
                String disconnectReason,
                List<String> warningLogs,
                List<String> consoleLogs
        ) {
            return new GroundMovementDecision(
                    Action.DISCONNECT,
                    disconnectReason,
                    0.0F,
                    0.0F,
                    0,
                    false,
                    0.0D,
                    warningLogs,
                    consoleLogs
            );
        }

        public static GroundMovementDecision applyMovement(
                int updatedFloatingTicks,
                boolean onGround,
                double fallDeltaY,
                List<String> warningLogs,
                List<String> consoleLogs
        ) {
            return new GroundMovementDecision(
                    Action.APPLY_MOVEMENT,
                    null,
                    0.0F,
                    0.0F,
                    updatedFloatingTicks,
                    onGround,
                    fallDeltaY,
                    warningLogs,
                    consoleLogs
            );
        }

        public Action getAction() {
            return action;
        }

        public String getDisconnectReason() {
            return disconnectReason;
        }

        public float getTeleportYaw() {
            return teleportYaw;
        }

        public float getTeleportPitch() {
            return teleportPitch;
        }

        public int getUpdatedFloatingTicks() {
            return updatedFloatingTicks;
        }

        public boolean isOnGround() {
            return onGround;
        }

        public double getFallDeltaY() {
            return fallDeltaY;
        }

        public List<String> getWarningLogs() {
            return warningLogs;
        }

        public List<String> getConsoleLogs() {
            return consoleLogs;
        }
    }
}
