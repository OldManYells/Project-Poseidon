package com.legacyminecraft.poseidon.network;

import org.bukkit.Location;

/**
 * Canonical policy for move-event triggering decisions in legacy movement packet handling.
 */
public final class PlayerMoveEventPolicy {
    private static final PlayerMoveEventPolicy INSTANCE = new PlayerMoveEventPolicy();

    private PlayerMoveEventPolicy() {
    }

    public static PlayerMoveEventPolicy getInstance() {
        return INSTANCE;
    }

    public boolean shouldApplyPositionFromPacket(boolean hasPosition, double y, double stance) {
        return hasPosition && !(hasPosition && y == -999.0D && stance == -999.0D);
    }

    public boolean hasSignificantMoveEventDelta(
            double lastPosX,
            double lastPosY,
            double lastPosZ,
            float lastYaw,
            float lastPitch,
            Location to
    ) {
        double delta = Math.pow(lastPosX - to.getX(), 2)
                + Math.pow(lastPosY - to.getY(), 2)
                + Math.pow(lastPosZ - to.getZ(), 2);
        float deltaAngle = Math.abs(lastYaw - to.getYaw()) + Math.abs(lastPitch - to.getPitch());
        return delta > 1f / 256 || deltaAngle > 10f;
    }

    public boolean shouldProcessMoveEvent(boolean significantDelta, boolean checkMovement, boolean playerDead) {
        return significantDelta && checkMovement && !playerDead;
    }

    public boolean hasInitializedMoveFromLocation(Location from) {
        return from.getX() != Double.MAX_VALUE;
    }

    public boolean shouldAbortAfterPluginTeleport(Location from, Location currentLocation, boolean justTeleported) {
        return !from.equals(currentLocation) && justTeleported;
    }
}
