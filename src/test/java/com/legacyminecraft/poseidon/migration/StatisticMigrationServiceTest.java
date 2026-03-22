package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.stats.StatisticRegistry;
import net.minecraft.server.Statistic;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatisticMigrationServiceTest {
    @Test
    public void statisticRegistryRegistersAndRejectsDuplicates() {
        Map registryById = new HashMap();
        List allStats = new ArrayList();

        Statistic stat = new Statistic(424242, "MigrationStat");
        StatisticRegistry.getInstance().register(stat, registryById, allStats);

        Assert.assertSame(stat, registryById.get(Integer.valueOf(424242)));
        Assert.assertTrue(allStats.contains(stat));

        try {
            StatisticRegistry.getInstance().register(new Statistic(424242, "DuplicateStat"), registryById, allStats);
            Assert.fail("Expected duplicate registration to throw");
        } catch (RuntimeException expected) {
            Assert.assertTrue(expected.getMessage().contains("Duplicate stat id"));
        }
    }
}
