package com.legacyminecraft.poseidon.world.stats;

import net.minecraft.server.AchievementMap;
import net.minecraft.server.Statistic;

import java.util.List;
import java.util.Map;

/**
 * Canonical statistic registration service.
 */
public final class StatisticRegistry {
    private static final StatisticRegistry INSTANCE = new StatisticRegistry();

    private StatisticRegistry() {
    }

    public static StatisticRegistry getInstance() {
        return INSTANCE;
    }

    public Statistic register(Statistic statistic, Map registryById, List allStatistics) {
        if (registryById.containsKey(Integer.valueOf(statistic.e))) {
            throw new RuntimeException(
                    "Duplicate stat id: \"" + ((Statistic) registryById.get(Integer.valueOf(statistic.e))).f
                            + "\" and \"" + statistic.f + "\" at id " + statistic.e);
        }

        allStatistics.add(statistic);
        registryById.put(Integer.valueOf(statistic.e), statistic);
        statistic.h = AchievementMap.a(statistic.e);
        return statistic;
    }
}
