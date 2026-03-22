package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.AchievementTranslationRegistry;

public class AchievementMap {

    public static AchievementMap a = new AchievementMap();

    private AchievementMap() {
    }

    public static String a(int i) {
        return AchievementTranslationRegistry.getInstance().lookup(i);
    }
}
