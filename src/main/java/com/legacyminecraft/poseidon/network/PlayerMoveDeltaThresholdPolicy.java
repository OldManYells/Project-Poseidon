package com.legacyminecraft.poseidon.network;

/**
 * Canonical threshold policy for deciding when move-event deltas are significant.
 */
public final class PlayerMoveDeltaThresholdPolicy {
    private static final PlayerMoveDeltaThresholdPolicy INSTANCE = new PlayerMoveDeltaThresholdPolicy();
    private static final double POSITION_DELTA_THRESHOLD = 1f / 256;
    private static final float ANGLE_DELTA_THRESHOLD = 10f;

    private PlayerMoveDeltaThresholdPolicy() {
    }

    public static PlayerMoveDeltaThresholdPolicy getInstance() {
        return INSTANCE;
    }

    public double positionDeltaThreshold() {
        return POSITION_DELTA_THRESHOLD;
    }

    public float angleDeltaThreshold() {
        return ANGLE_DELTA_THRESHOLD;
    }
}
