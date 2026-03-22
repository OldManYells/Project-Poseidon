package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical policy for whether incoming damage should be applied to a player.
 */
public final class PlayerDamagePolicySystem {
    private static final PlayerDamagePolicySystem INSTANCE = new PlayerDamagePolicySystem();

    private PlayerDamagePolicySystem() {
    }

    public static PlayerDamagePolicySystem getInstance() {
        return INSTANCE;
    }

    public boolean shouldApplyDamage(int damageImmunityTicks, boolean pvpEnabled, boolean attackerIsHuman, boolean arrowShotByHuman) {
        if (damageImmunityTicks > 0) {
            return false;
        }

        if (!pvpEnabled && (attackerIsHuman || arrowShotByHuman)) {
            return false;
        }

        return true;
    }
}
