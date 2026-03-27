package com.legacyminecraft.poseidon.network;

/**
 * Canonical sentinel policy for legacy movement packet "motion-only" position encoding.
 */
public final class MovementPacketSentinelPolicy {
    private static final MovementPacketSentinelPolicy INSTANCE = new MovementPacketSentinelPolicy();
    private static final double MOTION_SENTINEL = -999.0D;

    private MovementPacketSentinelPolicy() {
    }

    public static MovementPacketSentinelPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isMotionOnlySentinel(double y, double stance) {
        return y == MOTION_SENTINEL && stance == MOTION_SENTINEL;
    }
}
