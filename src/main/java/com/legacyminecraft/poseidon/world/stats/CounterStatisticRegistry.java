package com.legacyminecraft.poseidon.world.stats;

import net.minecraft.server.Statistic;

import java.util.List;

/**
 * Canonical registration helper for counter-statistic list membership.
 */
public final class CounterStatisticRegistry {
    private static final CounterStatisticRegistry INSTANCE = new CounterStatisticRegistry();

    private CounterStatisticRegistry() {
    }

    public static CounterStatisticRegistry getInstance() {
        return INSTANCE;
    }

    public Statistic registerInCounterList(Statistic statistic, List counterStatistics) {
        counterStatistics.add(statistic);
        return statistic;
    }
}
