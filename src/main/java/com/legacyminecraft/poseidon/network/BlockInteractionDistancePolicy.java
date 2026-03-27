package com.legacyminecraft.poseidon.network;

/**
 * Canonical distance policy for block placement/interact reach checks.
 */
public final class BlockInteractionDistancePolicy {
    private static final BlockInteractionDistancePolicy INSTANCE = new BlockInteractionDistancePolicy();
    private static final int PLACE_DISTANCE_SQUARED = 6 * 6;

    private BlockInteractionDistancePolicy() {
    }

    public static BlockInteractionDistancePolicy getInstance() {
        return INSTANCE;
    }

    public int placeDistanceSquared() {
        return PLACE_DISTANCE_SQUARED;
    }
}
