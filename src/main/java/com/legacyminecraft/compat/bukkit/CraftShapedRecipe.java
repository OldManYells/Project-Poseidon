package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat craft-shaped-recipe scaffold.
 */
public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe {
    public CraftShapedRecipe(ItemStack result) {
        super(result);
    }

    @Override
    public void addToCraftingManager() {
        RecipeRegistrationOrchestrationBehaviour.getInstance().registerShapedRecipe(this);
    }
}

