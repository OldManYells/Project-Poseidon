package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat furnace-recipes registry scaffold.
 */
public class FurnaceRecipes {
    private static final FurnaceRecipes INSTANCE = new FurnaceRecipes();

    private FurnaceRecipes() {
    }

    public static FurnaceRecipes getInstance() {
        return INSTANCE;
    }

    public void registerRecipe(int itemTypeId, ItemStack result) {
    }
}

