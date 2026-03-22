package com.legacyminecraft.poseidon.world.stats;

import net.minecraft.server.CraftingManager;
import net.minecraft.server.CraftingRecipe;
import net.minecraft.server.CraftingStatistic;
import net.minecraft.server.FurnaceRecipes;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import net.minecraft.server.Statistic;

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
        Iterator recipeIterator = CraftingManager.getInstance().b().iterator();

        while (recipeIterator.hasNext()) {
            CraftingRecipe recipe = (CraftingRecipe) recipeIterator.next();
            craftableItemIds.add(Integer.valueOf(recipe.b().id));
        }

        recipeIterator = FurnaceRecipes.getInstance().b().values().iterator();
        while (recipeIterator.hasNext()) {
            ItemStack smeltResult = (ItemStack) recipeIterator.next();
            craftableItemIds.add(Integer.valueOf(smeltResult.id));
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
