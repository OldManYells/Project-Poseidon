package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.StatisticTranslationBehaviour;

public class StatisticStorage {

    private static StatisticStorage a = new StatisticStorage();
    private final StatisticTranslationBehaviour statisticTranslationBehaviour = StatisticTranslationBehaviour.getInstance();

    private StatisticStorage() {}

    public static StatisticStorage a() {
        return a;
    }

    public String a(String s) {
        return statisticTranslationBehaviour.translate(s);
    }

    public String a(String s, Object... aobject) {
        return statisticTranslationBehaviour.format(s, aobject);
    }
}
