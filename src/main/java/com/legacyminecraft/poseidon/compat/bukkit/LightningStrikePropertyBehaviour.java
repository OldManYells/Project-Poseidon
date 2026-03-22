package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityWeatherStorm;

/**
 * Canonical behavior for CraftLightningStrike effect-state access.
 */
public final class LightningStrikePropertyBehaviour {
    private static final LightningStrikePropertyBehaviour INSTANCE = new LightningStrikePropertyBehaviour();

    private LightningStrikePropertyBehaviour() {
    }

    public static LightningStrikePropertyBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isEffect(EntityWeatherStorm lightning) {
        return lightning.isEffect;
    }
}

