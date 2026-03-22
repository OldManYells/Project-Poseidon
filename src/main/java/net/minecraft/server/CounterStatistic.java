package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.CounterStatisticRegistry;

public class CounterStatistic extends Statistic {
    private final CounterStatisticRegistry counterStatisticRegistry = CounterStatisticRegistry.getInstance();

    public CounterStatistic(int i, String s, Counter counter) {
        super(i, s, counter);
    }

    public CounterStatistic(int i, String s) {
        super(i, s);
    }

    public Statistic d() {
        super.d();
        return counterStatisticRegistry.registerInCounterList(this, StatisticList.c);
    }
}
