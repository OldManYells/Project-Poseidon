package com.legacyminecraft.poseidon.world.stats;

/**
 * Minimal achievement translation hook.
 */
public final class AchievementMap {
    private AchievementMap() {
    }

    public static String a(int achievementId) {
        return "achievement." + achievementId;
    }
}
