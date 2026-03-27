package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat craft-recipe contract.
 */
public interface CraftRecipe extends Recipe {
    void addToCraftingManager();
}
