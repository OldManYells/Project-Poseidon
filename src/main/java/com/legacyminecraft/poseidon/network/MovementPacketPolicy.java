package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet10Flying;
import org.bukkit.entity.Player;

/**
 * Canonical policy helpers for incoming movement packet validation/threshold decisions.
 */
public final class MovementPacketPolicy {
    private static final MovementPacketPolicy INSTANCE = new MovementPacketPolicy();

    private MovementPacketPolicy() {
    }

    public static MovementPacketPolicy getInstance() {
        return INSTANCE;
    }

    public boolean shouldReEnableMovementCheck(
            boolean checkMovement,
            double packetX,
            double packetY,
            double packetZ,
            double lastX,
            double lastY,
            double lastZ
    ) {
        if (checkMovement) {
            return false;
        }
        double deltaY = packetY - lastY;
        return packetX == lastX && deltaY * deltaY < 0.01D && packetZ == lastZ;
    }

    public boolean hasInvalidNumericPosition(Packet10Flying packet10flying, Player player, boolean disconnected) {
        return Double.isNaN(packet10flying.x)
                || Double.isNaN(packet10flying.y)
                || Double.isNaN(packet10flying.z)
                || Double.isNaN(packet10flying.stance) && player.isOnline() && !disconnected;
    }

    public boolean isVehicleCrashAttempt(double moveX, double moveZ) {
        double magnitudeSquared = moveX * moveX + moveZ * moveZ;
        return magnitudeSquared > 100.0D;
    }

    public boolean isIllegalStance(boolean isSleeping, double stanceDelta) {
        return !isSleeping && (stanceDelta > 1.65D || stanceDelta < 0.1D);
    }

    public boolean isIllegalHorizontalPosition(double x, double z) {
        return Math.abs(x) > 3.2E7D || Math.abs(z) > 3.2E7D;
    }

    public boolean shouldTeleportOrKickForSpeed(
            boolean speedHackCheckEnabled,
            boolean checkMovement,
            double movedDistanceSquared,
            double velocityDistanceSquared,
            double configuredDistanceThreshold
    ) {
        return speedHackCheckEnabled
                && movedDistanceSquared - velocityDistanceSquared > configuredDistanceThreshold
                && checkMovement;
    }

    public double normalizeVerticalDeltaAfterMove(double deltaY) {
        if (deltaY > -0.5D || deltaY < 0.5D) {
            return 0.0D;
        }
        return deltaY;
    }

    public boolean shouldMarkMovedWrongly(double movedDistanceSquared, boolean sleeping) {
        return movedDistanceSquared > 0.0625D && !sleeping;
    }

    public boolean shouldTeleportToLastGoodPosition(boolean hadNoInitialCollisions, boolean movedWrongly, boolean hasNoFinalCollisions, boolean sleeping) {
        return hadNoInitialCollisions && (movedWrongly || !hasNoFinalCollisions) && !sleeping;
    }

    public boolean shouldIncrementFloatingCounter(boolean allowFlight, boolean touchingGroundBelow, double deltaY) {
        return !allowFlight && !touchingGroundBelow && deltaY >= -0.03125D;
    }

    public boolean shouldKickForFloatingTicks(int floatingTicks) {
        return floatingTicks > 80;
    }
}
