package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.stats.CounterStatisticRegistry;
import net.minecraft.server.Statistic;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CounterStatisticRegistryServiceTest {
    private final CounterStatisticRegistry service = CounterStatisticRegistry.getInstance();

    @Test
    public void registerInCounterListAppendsAndReturnsSameStatistic() {
        Statistic statistic = new Statistic(999000, "poseidon.counter.test");
        List counterStatistics = new ArrayList();

        Statistic returned = service.registerInCounterList(statistic, counterStatistics);

        Assert.assertSame(statistic, returned);
        Assert.assertEquals(1, counterStatistics.size());
        Assert.assertSame(statistic, counterStatistics.get(0));
    }
}
