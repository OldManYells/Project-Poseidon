package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.stats.StatisticArrayBuilder;
import net.minecraft.server.Block;
import net.minecraft.server.Statistic;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StatisticArrayBuilderTest {
    private final StatisticArrayBuilder builder = StatisticArrayBuilder.getInstance();

    @Test
    public void mergeCopiesSourceWhenTargetMissing() {
        Statistic[] statistics = new Statistic[5];
        Statistic source = new Statistic(1, "source");
        statistics[1] = source;

        builder.mergeEquivalentBlockIdPair(statistics, 1, 2, new ArrayList(), new ArrayList(), new ArrayList());

        Assert.assertSame(source, statistics[2]);
        Assert.assertSame(source, statistics[1]);
    }

    @Test
    public void mergeReplacesSourceWithTargetAndCleansLists() {
        Statistic[] statistics = new Statistic[5];
        Statistic source = new Statistic(1, "source");
        Statistic target = new Statistic(2, "target");
        statistics[1] = source;
        statistics[2] = target;

        List allStats = new ArrayList();
        List mineStats = new ArrayList();
        List craftedStats = new ArrayList();
        allStats.add(source);
        mineStats.add(source);
        craftedStats.add(source);

        builder.mergeEquivalentBlockIdPair(statistics, 1, 2, allStats, mineStats, craftedStats);

        Assert.assertSame(target, statistics[1]);
        Assert.assertFalse(allStats.contains(source));
        Assert.assertFalse(mineStats.contains(source));
        Assert.assertFalse(craftedStats.contains(source));
    }

    @Test
    public void mergeNoopsWhenSourceAndTargetIdsMatch() {
        Statistic[] statistics = new Statistic[5];
        Statistic same = new Statistic(1, "same");
        statistics[1] = same;

        List allStats = new ArrayList();
        List mineStats = new ArrayList();
        List craftedStats = new ArrayList();
        allStats.add(same);
        mineStats.add(same);
        craftedStats.add(same);

        builder.mergeEquivalentBlockIdPair(statistics, 1, 1, allStats, mineStats, craftedStats);

        Assert.assertSame(same, statistics[1]);
        Assert.assertTrue(allStats.contains(same));
        Assert.assertTrue(mineStats.contains(same));
        Assert.assertTrue(craftedStats.contains(same));
    }

    @Test
    public void mergeAliasesMissingSourceToExistingTargetWithoutListMutation() {
        Statistic[] statistics = new Statistic[5];
        Statistic target = new Statistic(2, "target");
        statistics[2] = target;

        List allStats = new ArrayList();
        List mineStats = new ArrayList();
        List craftedStats = new ArrayList();

        builder.mergeEquivalentBlockIdPair(statistics, 1, 2, allStats, mineStats, craftedStats);

        Assert.assertSame(target, statistics[1]);
        Assert.assertTrue(allStats.isEmpty());
        Assert.assertTrue(mineStats.isEmpty());
        Assert.assertTrue(craftedStats.isEmpty());
    }

    @Test
    public void mergeLeavesListsUntouchedWhenBothSourceAndTargetMissing() {
        Statistic[] statistics = new Statistic[5];
        Statistic unrelated = new Statistic(4, "unrelated");

        List allStats = new ArrayList();
        List mineStats = new ArrayList();
        List craftedStats = new ArrayList();
        allStats.add(unrelated);
        mineStats.add(unrelated);
        craftedStats.add(unrelated);

        builder.mergeEquivalentBlockIdPair(statistics, 1, 2, allStats, mineStats, craftedStats);

        Assert.assertNull(statistics[1]);
        Assert.assertNull(statistics[2]);
        Assert.assertTrue(allStats.contains(unrelated));
        Assert.assertTrue(mineStats.contains(unrelated));
        Assert.assertTrue(craftedStats.contains(unrelated));
    }

    @Test
    public void normalizeEquivalentBlockIdsMergesStationaryLavaIntoFlowingLava() {
        Statistic[] statistics = new Statistic[256];
        Statistic stationaryLava = new Statistic(100, "stationary_lava");
        Statistic flowingLava = new Statistic(101, "flowing_lava");
        statistics[Block.STATIONARY_LAVA.id] = stationaryLava;
        statistics[Block.LAVA.id] = flowingLava;

        List allStats = new ArrayList();
        List mineStats = new ArrayList();
        List craftedStats = new ArrayList();
        allStats.add(stationaryLava);
        mineStats.add(stationaryLava);
        craftedStats.add(stationaryLava);

        builder.normalizeEquivalentBlockIds(statistics, allStats, mineStats, craftedStats);

        Assert.assertSame(flowingLava, statistics[Block.STATIONARY_LAVA.id]);
        Assert.assertFalse(allStats.contains(stationaryLava));
        Assert.assertFalse(mineStats.contains(stationaryLava));
        Assert.assertFalse(craftedStats.contains(stationaryLava));
    }

    @Test
    public void normalizeEquivalentBlockIdsReplacesAllDefinedSourceStatsWithTargets() {
        Statistic[] statistics = new Statistic[256];
        List allStats = new ArrayList();
        List mineStats = new ArrayList();
        List craftedStats = new ArrayList();

        int[][] equivalentPairs = new int[][]{
                {Block.STATIONARY_WATER.id, Block.WATER.id},
                {Block.STATIONARY_LAVA.id, Block.LAVA.id},
                {Block.JACK_O_LANTERN.id, Block.PUMPKIN.id},
                {Block.BURNING_FURNACE.id, Block.FURNACE.id},
                {Block.GLOWING_REDSTONE_ORE.id, Block.REDSTONE_ORE.id},
                {Block.DIODE_ON.id, Block.DIODE_OFF.id},
                {Block.REDSTONE_TORCH_ON.id, Block.REDSTONE_TORCH_OFF.id},
                {Block.RED_MUSHROOM.id, Block.BROWN_MUSHROOM.id},
                {Block.DOUBLE_STEP.id, Block.STEP.id},
                {Block.GRASS.id, Block.DIRT.id},
                {Block.SOIL.id, Block.DIRT.id}
        };

        int statIdSeed = 200000;
        for (int[] pair : equivalentPairs) {
            int sourceId = pair[0];
            int targetId = pair[1];
            Assert.assertTrue(sourceId != targetId);
            Statistic source = new Statistic(statIdSeed++, "source_" + sourceId);
            Statistic target = new Statistic(statIdSeed++, "target_" + targetId);
            statistics[sourceId] = source;
            statistics[targetId] = target;
            allStats.add(source);
            mineStats.add(source);
            craftedStats.add(source);
        }

        builder.normalizeEquivalentBlockIds(statistics, allStats, mineStats, craftedStats);

        for (int[] pair : equivalentPairs) {
            int sourceId = pair[0];
            int targetId = pair[1];
            Assert.assertSame(statistics[targetId], statistics[sourceId]);
        }

        Assert.assertEquals(0, allStats.size());
        Assert.assertEquals(0, mineStats.size());
        Assert.assertEquals(0, craftedStats.size());
    }
}
