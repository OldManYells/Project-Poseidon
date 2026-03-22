package com.legacyminecraft.poseidon.world.stats;

import net.minecraft.server.Achievement;
import net.minecraft.server.AchievementList;

/**
 * Canonical achievement registration and bounds tracking service.
 */
public final class AchievementRegistry {
    private static final AchievementRegistry INSTANCE = new AchievementRegistry();

    private AchievementRegistry() {
    }

    public static AchievementRegistry getInstance() {
        return INSTANCE;
    }

    public void trackBounds(int x, int y) {
        if (x < AchievementList.a) {
            AchievementList.a = x;
        }
        if (y < AchievementList.b) {
            AchievementList.b = y;
        }
        if (x > AchievementList.c) {
            AchievementList.c = x;
        }
        if (y > AchievementList.d) {
            AchievementList.d = y;
        }
    }

    public Achievement register(Achievement achievement) {
        achievement.d();
        AchievementList.e.add(achievement);
        return achievement;
    }
}
