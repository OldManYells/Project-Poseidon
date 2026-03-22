package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.StatisticTranslationBehaviour;

public class StatisticCollector {

    private static final StatisticTranslationBehaviour statisticTranslationService = StatisticTranslationBehaviour.getInstance();

    public StatisticCollector() {}

    public static String a(String s) {
        return statisticTranslationService.translate(s);
    }

    public static String a(String s, Object... aobject) {
        return statisticTranslationService.format(s, aobject);
    }
}
