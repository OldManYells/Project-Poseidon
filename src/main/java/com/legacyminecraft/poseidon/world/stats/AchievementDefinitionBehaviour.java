package com.legacyminecraft.poseidon.world.stats;

/**
 * Canonical helper for deriving legacy achievement stat ids and translation keys.
 */
public final class AchievementDefinitionBehaviour {
    private static final AchievementDefinitionBehaviour INSTANCE = new AchievementDefinitionBehaviour();
    private static final int ACHIEVEMENT_STAT_BASE_ID = 5242880;

    private final StatisticTranslationBehaviour statisticTranslationBehaviour = StatisticTranslationBehaviour.getInstance();
    private final AchievementRegistry achievementRegistry = AchievementRegistry.getInstance();

    private AchievementDefinitionBehaviour() {
    }

    public static AchievementDefinitionBehaviour getInstance() {
        return INSTANCE;
    }

    public int toStatisticId(int achievementId) {
        return ACHIEVEMENT_STAT_BASE_ID + achievementId;
    }

    public String resolveTitle(String achievementKey) {
        return statisticTranslationBehaviour.translate("achievement." + achievementKey);
    }

    public String resolveDescription(String achievementKey) {
        return statisticTranslationBehaviour.translate("achievement." + achievementKey + ".desc");
    }

    public void trackBounds(int x, int y) {
        achievementRegistry.trackBounds(x, y);
    }
}
