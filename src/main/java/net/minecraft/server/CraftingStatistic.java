package net.minecraft.server;

import com.legacyminecraft.poseidon.world.stats.CraftingStatisticBehaviour;

public class CraftingStatistic extends Statistic {
    private static final CraftingStatisticBehaviour CRAFTING_STATISTIC_BEHAVIOUR = CraftingStatisticBehaviour.getInstance();

    private final int a;

    public CraftingStatistic(int i, String s, int j) {
        super(i, s);
        this.a = CRAFTING_STATISTIC_BEHAVIOUR.resolveCraftedItemId(j);
    }

    public int poseidonGetCraftedItemId() {
        return this.a;
    }
}
