package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat furnace recipe scaffold.
 */
public class FurnaceRecipe implements Recipe {
    private final ItemStack result;
    private final MaterialData input;

    public FurnaceRecipe(ItemStack result) {
        this(result, new MaterialData(0, 0));
    }

    public FurnaceRecipe(ItemStack result, MaterialData input) {
        this.result = result;
        this.input = input;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    public MaterialData getInput() {
        return input;
    }
}
