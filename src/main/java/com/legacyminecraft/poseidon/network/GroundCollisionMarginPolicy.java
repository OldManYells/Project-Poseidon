package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for legacy collision margin expansion/shrink checks in movement resolution.
 */
public final class GroundCollisionMarginPolicy {
    private static final GroundCollisionMarginPolicy INSTANCE = new GroundCollisionMarginPolicy();
    private static final float COLLISION_MARGIN = 0.0625F;

    private GroundCollisionMarginPolicy() {
    }

    public static GroundCollisionMarginPolicy getInstance() {
        return INSTANCE;
    }

    public float collisionMargin() {
        return COLLISION_MARGIN;
    }
}
