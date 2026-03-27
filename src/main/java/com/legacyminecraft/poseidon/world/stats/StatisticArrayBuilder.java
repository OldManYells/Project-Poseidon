package com.legacyminecraft.poseidon.world.stats;


import java.util.List;

/**
 * Canonical builder for statistic arrays and block-id alias normalization.
 */
public final class StatisticArrayBuilder {
    private static final StatisticArrayBuilder INSTANCE = new StatisticArrayBuilder();
    private final StatisticTranslationBehaviour statisticTranslationBehaviour = StatisticTranslationBehaviour.getInstance();

    private StatisticArrayBuilder() {
    }

    public static StatisticArrayBuilder getInstance() {
        return INSTANCE;
    }

    public Statistic[] createMineBlockStats(String translationKey, int statisticBaseId, List allStatistics, List mineBlockStatistics, List craftedStatistics) {
        Statistic[] statistics = new Statistic[256];

        for (int blockId = 0; blockId < 256; ++blockId) {
            if (Block.byId[blockId] != null && Block.byId[blockId].m()) {
                String translated = statisticTranslationBehaviour.format(translationKey, new Object[]{Block.byId[blockId].k()});
                statistics[blockId] = (new CraftingStatistic(statisticBaseId + blockId, translated, blockId)).d();
                mineBlockStatistics.add((CraftingStatistic) statistics[blockId]);
            }
        }

        normalizeEquivalentBlockIds(statistics, allStatistics, mineBlockStatistics, craftedStatistics);
        return statistics;
    }

    public Statistic[] createUseStats(Statistic[] statistics, String translationKey, int statisticBaseId, int startId, int endId, List allStatistics, List mineBlockStatistics, List craftedStatistics, List useItemStatistics) {
        if (statistics == null) {
            statistics = new Statistic[32000];
        }

        for (int id = startId; id < endId; ++id) {
            if (Item.byId[id] != null) {
                String translated = statisticTranslationBehaviour.format(translationKey, new Object[]{Item.byId[id].j()});
                statistics[id] = (new CraftingStatistic(statisticBaseId + id, translated, id)).d();
                if (id >= Block.byId.length) {
                    useItemStatistics.add((CraftingStatistic) statistics[id]);
                }
            }
        }

        normalizeEquivalentBlockIds(statistics, allStatistics, mineBlockStatistics, craftedStatistics);
        return statistics;
    }

    public Statistic[] createBreakStats(Statistic[] statistics, String translationKey, int statisticBaseId, int startId, int endId, List allStatistics, List mineBlockStatistics, List craftedStatistics) {
        if (statistics == null) {
            statistics = new Statistic[32000];
        }

        for (int id = startId; id < endId; ++id) {
            if (Item.byId[id] != null && Item.byId[id].f()) {
                String translated = statisticTranslationBehaviour.format(translationKey, new Object[]{Item.byId[id].j()});
                statistics[id] = (new CraftingStatistic(statisticBaseId + id, translated, id)).d();
            }
        }

        normalizeEquivalentBlockIds(statistics, allStatistics, mineBlockStatistics, craftedStatistics);
        return statistics;
    }

    public void normalizeEquivalentBlockIds(Statistic[] statistics, List allStatistics, List mineBlockStatistics, List craftedStatistics) {
        mergeEquivalentBlockIdPair(statistics, Block.STATIONARY_WATER.id, Block.WATER.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.STATIONARY_LAVA.id, Block.LAVA.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.JACK_O_LANTERN.id, Block.PUMPKIN.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.BURNING_FURNACE.id, Block.FURNACE.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.GLOWING_REDSTONE_ORE.id, Block.REDSTONE_ORE.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.DIODE_ON.id, Block.DIODE_OFF.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.REDSTONE_TORCH_ON.id, Block.REDSTONE_TORCH_OFF.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.RED_MUSHROOM.id, Block.BROWN_MUSHROOM.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.DOUBLE_STEP.id, Block.STEP.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.GRASS.id, Block.DIRT.id, allStatistics, mineBlockStatistics, craftedStatistics);
        mergeEquivalentBlockIdPair(statistics, Block.SOIL.id, Block.DIRT.id, allStatistics, mineBlockStatistics, craftedStatistics);
    }

    public void mergeEquivalentBlockIdPair(Statistic[] statistics, int sourceId, int targetId, List allStatistics, List mineBlockStatistics, List craftedStatistics) {
        if (sourceId == targetId) {
            return;
        }

        Statistic sourceStatistic = statistics[sourceId];
        Statistic targetStatistic = statistics[targetId];

        if (sourceStatistic != null && targetStatistic == null) {
            statistics[targetId] = sourceStatistic;
            return;
        }

        if (sourceStatistic != null) {
            allStatistics.remove(sourceStatistic);
            mineBlockStatistics.remove(sourceStatistic);
            craftedStatistics.remove(sourceStatistic);
        }

        statistics[sourceId] = statistics[targetId];
    }
}
