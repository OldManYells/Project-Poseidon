package com.legacyminecraft.poseidon.network;

import net.minecraft.server.EntityPlayer;
import net.minecraft.server.WorldServer;

/**
 * Canonical coordinator for post-move collision checks, moved-wrongly detection, and floating guards.
 */
public final class GroundMovementResolutionSystem {
    private static final GroundMovementResolutionSystem INSTANCE = new GroundMovementResolutionSystem();
    private static final float COLLISION_MARGIN = 0.0625F;

    private final MovementPacketPolicy movementPacketPolicy = MovementPacketPolicy.getInstance();
    private final FloatingTickGuardSystem floatingTickGuardSystem = FloatingTickGuardSystem.getInstance();

    private GroundMovementResolutionSystem() {
    }

    public static GroundMovementResolutionSystem getInstance() {
        return INSTANCE;
    }

    public GroundMovementResult resolveGroundMovement(
            EntityPlayer player,
            WorldServer worldServer,
            double targetX,
            double targetY,
            double targetZ,
            float targetYaw,
            float targetPitch,
            double deltaX,
            double deltaY,
            double deltaZ,
            boolean allowFlight,
            int floatingTicks
    ) {
        boolean hadNoInitialCollisions = worldServer.getEntities(
                player,
                player.boundingBox.clone().shrink((double) COLLISION_MARGIN, (double) COLLISION_MARGIN, (double) COLLISION_MARGIN)
        ).size() == 0;

        player.move(deltaX, deltaY, deltaZ);

        double adjustedDeltaX = targetX - player.locX;
        double adjustedDeltaY = movementPacketPolicy.normalizeVerticalDeltaAfterMove(targetY - player.locY);
        double adjustedDeltaZ = targetZ - player.locZ;
        double movedDistanceSquared = adjustedDeltaX * adjustedDeltaX + adjustedDeltaY * adjustedDeltaY + adjustedDeltaZ * adjustedDeltaZ;
        boolean movedWrongly = movementPacketPolicy.shouldMarkMovedWrongly(movedDistanceSquared, player.isSleeping());

        double expectedX = player.locX;
        double expectedY = player.locY;
        double expectedZ = player.locZ;

        player.setLocation(targetX, targetY, targetZ, targetYaw, targetPitch);

        boolean hasNoFinalCollisions = worldServer.getEntities(
                player,
                player.boundingBox.clone().shrink((double) COLLISION_MARGIN, (double) COLLISION_MARGIN, (double) COLLISION_MARGIN)
        ).size() == 0;

        boolean shouldTeleportToLastGoodPosition = movementPacketPolicy.shouldTeleportToLastGoodPosition(
                hadNoInitialCollisions,
                movedWrongly,
                hasNoFinalCollisions,
                player.isSleeping()
        );

        FloatingTickGuardSystem.FloatingDecision floatingDecision = floatingTickGuardSystem.evaluate(
                allowFlight,
                worldServer.b(player.boundingBox.clone().b((double) COLLISION_MARGIN, (double) COLLISION_MARGIN, (double) COLLISION_MARGIN).a(0.0D, -0.55D, 0.0D)),
                adjustedDeltaY,
                floatingTicks
        );

        return new GroundMovementResult(
                movedWrongly,
                expectedX,
                expectedY,
                expectedZ,
                shouldTeleportToLastGoodPosition,
                floatingDecision.getUpdatedFloatingTicks(),
                floatingDecision.shouldKick(),
                floatingDecision.getDisconnectReason()
        );
    }

    public String createMovedWronglyWarningMessage(String playerName) {
        return playerName + " moved wrongly!";
    }

    public String createMovedWronglyReceivedPosition(double x, double y, double z) {
        return "Got position " + x + ", " + y + ", " + z;
    }

    public String createMovedWronglyExpectedPosition(double x, double y, double z) {
        return "Expected " + x + ", " + y + ", " + z;
    }

    public String createFloatingKickLogMessage(String playerName) {
        return floatingTickGuardSystem.createFloatingKickLogMessage(playerName);
    }

    public static final class GroundMovementResult {
        private final boolean movedWrongly;
        private final double expectedX;
        private final double expectedY;
        private final double expectedZ;
        private final boolean teleportToLastGoodPosition;
        private final int updatedFloatingTicks;
        private final boolean kickForFloating;
        private final String floatingDisconnectReason;

        private GroundMovementResult(
                boolean movedWrongly,
                double expectedX,
                double expectedY,
                double expectedZ,
                boolean teleportToLastGoodPosition,
                int updatedFloatingTicks,
                boolean kickForFloating,
                String floatingDisconnectReason
        ) {
            this.movedWrongly = movedWrongly;
            this.expectedX = expectedX;
            this.expectedY = expectedY;
            this.expectedZ = expectedZ;
            this.teleportToLastGoodPosition = teleportToLastGoodPosition;
            this.updatedFloatingTicks = updatedFloatingTicks;
            this.kickForFloating = kickForFloating;
            this.floatingDisconnectReason = floatingDisconnectReason;
        }

        public boolean isMovedWrongly() {
            return movedWrongly;
        }

        public double getExpectedX() {
            return expectedX;
        }

        public double getExpectedY() {
            return expectedY;
        }

        public double getExpectedZ() {
            return expectedZ;
        }

        public boolean shouldTeleportToLastGoodPosition() {
            return teleportToLastGoodPosition;
        }

        public int getUpdatedFloatingTicks() {
            return updatedFloatingTicks;
        }

        public boolean shouldKickForFloating() {
            return kickForFloating;
        }

        public String getFloatingDisconnectReason() {
            return floatingDisconnectReason;
        }
    }
}
