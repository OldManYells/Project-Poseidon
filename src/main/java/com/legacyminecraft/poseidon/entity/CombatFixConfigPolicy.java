package com.legacyminecraft.poseidon.entity;

/**
 * Canonical config-key policy for legacy combat/movement fix toggles.
 */
public final class CombatFixConfigPolicy {
    private static final CombatFixConfigPolicy INSTANCE = new CombatFixConfigPolicy();
    private static final String DROWNING_PUSH_DOWN_FIX_KEY = "settings.fix-drowning-push-down.enabled";
    private static final String PLAYER_KNOCKBACK_FIX_KEY = "settings.player-knockback-fix.enabled";
    private static final String SKELETON_SHOOTING_SOUND_FIX_KEY = "world.settings.skeleton-shooting-sound-fix.enabled";
    private static final boolean DROWNING_PUSH_DOWN_FIX_DEFAULT = true;
    private static final boolean PLAYER_KNOCKBACK_FIX_DEFAULT = true;
    private static final boolean SKELETON_SHOOTING_SOUND_FIX_DEFAULT = true;

    private CombatFixConfigPolicy() {
    }

    public static CombatFixConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String drowningPushDownFixKey() {
        return DROWNING_PUSH_DOWN_FIX_KEY;
    }

    public boolean drowningPushDownFixDefault() {
        return DROWNING_PUSH_DOWN_FIX_DEFAULT;
    }

    public String playerKnockbackFixKey() {
        return PLAYER_KNOCKBACK_FIX_KEY;
    }

    public boolean playerKnockbackFixDefault() {
        return PLAYER_KNOCKBACK_FIX_DEFAULT;
    }

    public String skeletonShootingSoundFixKey() {
        return SKELETON_SHOOTING_SOUND_FIX_KEY;
    }

    public boolean skeletonShootingSoundFixDefault() {
        return SKELETON_SHOOTING_SOUND_FIX_DEFAULT;
    }
}
