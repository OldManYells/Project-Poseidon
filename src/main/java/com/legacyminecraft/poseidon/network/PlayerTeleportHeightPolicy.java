package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for legacy teleport packet stance/eye-height offsets.
 */
public final class PlayerTeleportHeightPolicy {
    private static final PlayerTeleportHeightPolicy INSTANCE = new PlayerTeleportHeightPolicy();
    private static final double LEGACY_EYE_HEIGHT_OFFSET = 1.6200000047683716D;

    private PlayerTeleportHeightPolicy() {
    }

    public static PlayerTeleportHeightPolicy getInstance() {
        return INSTANCE;
    }

    public double legacyEyeHeightOffset() {
        return LEGACY_EYE_HEIGHT_OFFSET;
    }
}
