package com.legacyminecraft.poseidon.network;

/**
 * Canonical sentinel policy for uninitialized move-event state coordinates.
 */
public final class PlayerMoveInitializationPolicy {
    private static final PlayerMoveInitializationPolicy INSTANCE = new PlayerMoveInitializationPolicy();
    private static final double UNINITIALIZED_COORDINATE = Double.MAX_VALUE;
    private static final float UNINITIALIZED_ROTATION = Float.MAX_VALUE;

    private PlayerMoveInitializationPolicy() {
    }

    public static PlayerMoveInitializationPolicy getInstance() {
        return INSTANCE;
    }

    public double uninitializedCoordinate() {
        return UNINITIALIZED_COORDINATE;
    }

    public float uninitializedRotation() {
        return UNINITIALIZED_ROTATION;
    }

    public boolean isInitializedCoordinate(double value) {
        return value != UNINITIALIZED_COORDINATE;
    }
}
