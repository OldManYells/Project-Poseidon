package com.legacyminecraft.poseidon.world.stats;


/**
 * Canonical factory for counter-statistic construction and registration policy.
 */
public final class CounterStatisticFactoryBehaviour {
    private static final CounterStatisticFactoryBehaviour INSTANCE = new CounterStatisticFactoryBehaviour();

    private CounterStatisticFactoryBehaviour() {
    }

    public static CounterStatisticFactoryBehaviour getInstance() {
        return INSTANCE;
    }

    public Statistic createCore(int id, String translatedName) {
        return (new CounterStatistic(id, translatedName)).e().d();
    }

    public Statistic createCore(int id, String translatedName, Counter formatter) {
        return (new CounterStatistic(id, translatedName, formatter)).e().d();
    }

    public Statistic createCounterOnly(int id, String translatedName) {
        return (new CounterStatistic(id, translatedName)).d();
    }
}
