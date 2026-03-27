package com.legacyminecraft.poseidon.world.stats;


import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

/**
 * Canonical crafting-statistic bootstrap service.
 */
public final class CraftingStatisticBootstrap {
    private static final CraftingStatisticBootstrap INSTANCE = new CraftingStatisticBootstrap();
    private final StatisticTranslationBehaviour statisticTranslationBehaviour = StatisticTranslationBehaviour.getInstance();

    private CraftingStatisticBootstrap() {
    }

    public static CraftingStatisticBootstrap getInstance() {
        return INSTANCE;
    }

    public Statistic[] initializeCraftingStatistics(boolean blockStatisticsInitialized, boolean itemStatisticsInitialized, List allStatistics, List mineBlockStatistics, List craftedStatistics) {
        if (!blockStatisticsInitialized || !itemStatisticsInitialized) {
            return null;
        }

        HashSet craftableItemIds = new HashSet();
        for (int itemId = 0; itemId < Item.byId.length; ++itemId) {
            if (Item.byId[itemId] != null) {
                craftableItemIds.add(Integer.valueOf(itemId));
            }
        }

        Statistic[] craftingStatistics = new Statistic[32000];
        Iterator idIterator = craftableItemIds.iterator();
        while (idIterator.hasNext()) {
            Integer itemId = (Integer) idIterator.next();
            if (Item.byId[itemId.intValue()] != null) {
                String translated = statisticTranslationBehaviour.format("stat.craftItem", new Object[]{Item.byId[itemId.intValue()].j()});
                craftingStatistics[itemId.intValue()] = (new CraftingStatistic(16842752 + itemId.intValue(), translated, itemId.intValue())).d();
            }
        }

        StatisticArrayBuilder.getInstance().normalizeEquivalentBlockIds(craftingStatistics, allStatistics, mineBlockStatistics, craftedStatistics);
        return craftingStatistics;
    }
}
