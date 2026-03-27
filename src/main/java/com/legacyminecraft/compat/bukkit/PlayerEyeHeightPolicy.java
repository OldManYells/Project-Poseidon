package com.legacyminecraft.compat.bukkit;

/**
 * Canonical policy for CraftPlayer eye-height calculations.
 */
public final class PlayerEyeHeightPolicy {
    private static final PlayerEyeHeightPolicy INSTANCE = new PlayerEyeHeightPolicy();

    private PlayerEyeHeightPolicy() {
    }

    public static PlayerEyeHeightPolicy getInstance() {
        return INSTANCE;
    }

    public double resolveEyeHeight(boolean ignoreSneaking, boolean sneaking) {
        if (ignoreSneaking) {
            return 1.62D;
        }
        if (sneaking) {
            return 1.42D;
        }
        return 1.62D;
    }
}
