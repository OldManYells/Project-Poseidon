package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.stats.CraftingStatisticBootstrap;
import net.minecraft.server.Statistic;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CraftingStatisticBootstrapTest {
    @Test
    public void returnsNullWhenBootstrapFlagsAreIncomplete() {
        List allStats = new ArrayList();
        List mineStats = new ArrayList();
        List craftedStats = new ArrayList();

        Statistic[] result = CraftingStatisticBootstrap.getInstance().initializeCraftingStatistics(false, true, allStats, mineStats, craftedStats);
        Assert.assertNull(result);

        result = CraftingStatisticBootstrap.getInstance().initializeCraftingStatistics(true, false, allStats, mineStats, craftedStats);
        Assert.assertNull(result);
    }
}
