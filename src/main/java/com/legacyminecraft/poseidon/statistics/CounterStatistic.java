package com.legacyminecraft.poseidon.statistics;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public class CounterStatistic extends Statistic {

    public CounterStatistic(int i, String s, Counter counter) {
        super(i, s, counter);
    }

    public CounterStatistic(int i, String s) {
        super(i, s);
    }

    public Statistic d() {
        super.d();
        StatisticList.c.add(this);
        return this;
    }
}
