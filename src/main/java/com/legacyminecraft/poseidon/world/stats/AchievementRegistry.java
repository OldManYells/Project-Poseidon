package com.legacyminecraft.poseidon.world.stats;


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

    public Object registerRaw(Object achievement) {
        if (achievement == null) {
            return null;
        }
        try {
            java.lang.reflect.Method method = achievement.getClass().getMethod("d");
            method.invoke(achievement);
        } catch (ReflectiveOperationException ignored) {
            // no-op for lean migration scaffolds
        }
        AchievementList.e.add(achievement);
        return achievement;
    }
}
