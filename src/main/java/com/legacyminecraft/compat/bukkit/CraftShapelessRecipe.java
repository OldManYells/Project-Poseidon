package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat craft-shapeless-recipe scaffold.
 */
public class CraftShapelessRecipe extends ShapelessRecipe implements CraftRecipe {
    public CraftShapelessRecipe(ItemStack result) {
        super(result);
    }

    @Override
    public void addToCraftingManager() {
        RecipeRegistrationOrchestrationBehaviour.getInstance().registerShapelessRecipe(this);
    }
}

