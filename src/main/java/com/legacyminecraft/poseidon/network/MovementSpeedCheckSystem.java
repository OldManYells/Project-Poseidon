package com.legacyminecraft.poseidon.network;

import com.legacyminecraft.poseidon.PoseidonConfig;

/**
 * Canonical coordinator for speed-check configuration and violation actions.
 */
public final class MovementSpeedCheckSystem {
    private static final MovementSpeedCheckSystem INSTANCE = new MovementSpeedCheckSystem();
    private static final String DEFAULT_KICK_MESSAGE = "You moved too quickly :( (Hacking?)";

    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();

    private MovementSpeedCheckSystem() {
    }

    public static MovementSpeedCheckSystem getInstance() {
        return INSTANCE;
    }

    public SpeedCheckDecision evaluateConfiguredDecision(
            boolean checkMovement,
            double movedDistanceSquared,
            double velocityDistanceSquared
    ) {
        PoseidonConfig config = PoseidonConfig.getInstance();
        boolean speedHackCheckEnabled =
                (boolean) config.getConfigOption("world.settings.speed-hack-check.enabled", true);
        double speedHackThreshold =
                (double) config.getConfigOption("world.settings.speed-hack-check.distance", 100.0D);
        boolean shouldTeleportOnViolation =
                (boolean) config.getConfigOption("world.settings.speed-hack-check.teleport", true);

        return evaluateWithOptions(
                speedHackCheckEnabled,
                checkMovement,
                movedDistanceSquared,
                velocityDistanceSquared,
                speedHackThreshold,
                shouldTeleportOnViolation
        );
    }

    public SpeedCheckDecision evaluateWithOptions(
            boolean speedHackCheckEnabled,
            boolean checkMovement,
            double movedDistanceSquared,
            double velocityDistanceSquared,
            double configuredDistanceThreshold,
            boolean shouldTeleportOnViolation
    ) {
        boolean violation = movementPacketPolicy.shouldTeleportOrKickForSpeed(
                speedHackCheckEnabled,
                checkMovement,
                movedDistanceSquared,
                velocityDistanceSquared,
                configuredDistanceThreshold
        );
        if (!violation) {
            return SpeedCheckDecision.allow();
        }

        if (shouldTeleportOnViolation) {
            return SpeedCheckDecision.teleportBack();
        }
        return SpeedCheckDecision.disconnect(DEFAULT_KICK_MESSAGE);
    }

    public String createSpeedViolationLogMessage(String playerName, double deltaX, double deltaY, double deltaZ) {
        return playerName + " moved too quickly! " + deltaX + "," + deltaY + "," + deltaZ
                + " (" + deltaX + ", " + deltaY + ", " + deltaZ + ")";
    }

    public static final class SpeedCheckDecision {
        private final boolean violation;
        private final boolean teleportBack;
        private final String disconnectReason;

        private SpeedCheckDecision(boolean violation, boolean teleportBack, String disconnectReason) {
            this.violation = violation;
            this.teleportBack = teleportBack;
            this.disconnectReason = disconnectReason;
        }

        public static SpeedCheckDecision allow() {
            return new SpeedCheckDecision(false, false, null);
        }

        public static SpeedCheckDecision teleportBack() {
            return new SpeedCheckDecision(true, true, null);
        }

        public static SpeedCheckDecision disconnect(String disconnectReason) {
            return new SpeedCheckDecision(true, false, disconnectReason);
        }

        public boolean isViolation() {
            return violation;
        }

        public boolean shouldTeleportBack() {
            return teleportBack;
        }

        public String getDisconnectReason() {
            return disconnectReason;
        }
    }
}
