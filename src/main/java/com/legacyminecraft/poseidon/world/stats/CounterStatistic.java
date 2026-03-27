package com.legacyminecraft.poseidon.world.stats;

/**
 * Minimal counter-backed statistic implementation.
 */
public final class CounterStatistic extends Statistic implements CounterContract {
    public CounterStatistic(int id, String name) {
        super(id, name);
    }

    public CounterStatistic(int id, String name, Counter counter) {
        super(id, name, counter);
    }
}
