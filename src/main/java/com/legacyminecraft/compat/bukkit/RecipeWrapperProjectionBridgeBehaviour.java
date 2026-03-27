package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting Bukkit recipe views to CraftBukkit recipe wrappers.
 */
public final class RecipeWrapperProjectionBridgeBehaviour {
    private static final RecipeWrapperProjectionBridgeBehaviour INSTANCE = new RecipeWrapperProjectionBridgeBehaviour();

    private RecipeWrapperProjectionBridgeBehaviour() {
    }

    public static RecipeWrapperProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftRecipe resolveCraftRecipe(Recipe recipe) {
        if (recipe instanceof CraftRecipe) {
            return (CraftRecipe) recipe;
        }
        return null;
    }

    public CraftFurnaceRecipe resolveCraftFurnaceRecipe(FurnaceRecipe recipe) {
        if (recipe instanceof CraftFurnaceRecipe) {
            return (CraftFurnaceRecipe) recipe;
        }
        return null;
    }

    public CraftShapedRecipe resolveCraftShapedRecipe(ShapedRecipe recipe) {
        if (recipe instanceof CraftShapedRecipe) {
            return (CraftShapedRecipe) recipe;
        }
        return null;
    }

    public CraftShapelessRecipe resolveCraftShapelessRecipe(ShapelessRecipe recipe) {
        if (recipe instanceof CraftShapelessRecipe) {
            return (CraftShapelessRecipe) recipe;
        }
        return null;
    }
}
