package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat craft-furnace-recipe scaffold.
 */
public class CraftFurnaceRecipe extends FurnaceRecipe implements CraftRecipe {
    public CraftFurnaceRecipe(ItemStack result, MaterialData input) {
        super(result, input);
    }

    @Override
    public void addToCraftingManager() {
        RecipeRegistrationOrchestrationBehaviour.getInstance().registerFurnaceRecipe(this);
    }
}

