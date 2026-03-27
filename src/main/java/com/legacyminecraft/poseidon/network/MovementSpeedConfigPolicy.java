package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for movement speed-check controls.
 */
public final class MovementSpeedConfigPolicy {
    private static final MovementSpeedConfigPolicy INSTANCE = new MovementSpeedConfigPolicy();
    private static final String SPEED_CHECK_ENABLED_KEY = "world.settings.speed-hack-check.enabled";
    private static final boolean SPEED_CHECK_ENABLED_DEFAULT = true;
    private static final String SPEED_CHECK_DISTANCE_KEY = "world.settings.speed-hack-check.distance";
    private static final double SPEED_CHECK_DISTANCE_DEFAULT = 100.0D;
    private static final String SPEED_CHECK_TELEPORT_KEY = "world.settings.speed-hack-check.teleport";
    private static final boolean SPEED_CHECK_TELEPORT_DEFAULT = true;

    private MovementSpeedConfigPolicy() {
    }

    public static MovementSpeedConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String speedCheckEnabledKey() {
        return SPEED_CHECK_ENABLED_KEY;
    }

    public boolean speedCheckEnabledDefault() {
        return SPEED_CHECK_ENABLED_DEFAULT;
    }

    public String speedCheckDistanceKey() {
        return SPEED_CHECK_DISTANCE_KEY;
    }

    public double speedCheckDistanceDefault() {
        return SPEED_CHECK_DISTANCE_DEFAULT;
    }

    public String speedCheckTeleportKey() {
        return SPEED_CHECK_TELEPORT_KEY;
    }

    public boolean speedCheckTeleportDefault() {
        return SPEED_CHECK_TELEPORT_DEFAULT;
    }
}
