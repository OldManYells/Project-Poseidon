package com.legacyminecraft.poseidon.world.stats;

/**
 * Canonical behaviour for crafting-statistic wrapper state.
 */
public final class CraftingStatisticBehaviour {
    private static final CraftingStatisticBehaviour INSTANCE = new CraftingStatisticBehaviour();

    private CraftingStatisticBehaviour() {
    }

    public static CraftingStatisticBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveCraftedItemId(int craftedItemId) {
        return craftedItemId;
    }
}
