package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat crafting-manager scaffold.
 */
public class CraftingManager {
    private static final CraftingManager INSTANCE = new CraftingManager();

    private CraftingManager() {
    }

    public static CraftingManager getInstance() {
        return INSTANCE;
    }

    public void registerShapedRecipe(ItemStack result, Object[] data) {
    }

    public void registerShapelessRecipe(ItemStack result, Object[] data) {
    }
}

