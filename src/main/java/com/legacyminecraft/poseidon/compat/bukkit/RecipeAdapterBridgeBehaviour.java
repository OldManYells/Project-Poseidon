package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;
import org.bukkit.craftbukkit.inventory.CraftFurnaceRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapedRecipe;
import org.bukkit.craftbukkit.inventory.CraftShapelessRecipe;

/**
 * Canonical behaviour for adapting Bukkit recipe types into CraftBukkit recipe wrappers.
 */
public final class RecipeAdapterBridgeBehaviour {
    private static final RecipeAdapterBridgeBehaviour INSTANCE = new RecipeAdapterBridgeBehaviour();

    private RecipeAdapterBridgeBehaviour() {
    }

    public static RecipeAdapterBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftFurnaceRecipe fromBukkitFurnaceRecipe(FurnaceRecipe recipe) {
        if (recipe instanceof CraftFurnaceRecipe) {
            return (CraftFurnaceRecipe) recipe;
        }
        return new CraftFurnaceRecipe(recipe.getResult(), recipe.getInput());
    }

    public CraftShapedRecipe fromBukkitShapedRecipe(ShapedRecipe recipe) {
        if (recipe instanceof CraftShapedRecipe) {
            return (CraftShapedRecipe) recipe;
        }

        CraftShapedRecipe adaptedRecipe = new CraftShapedRecipe(recipe.getResult());
        String[] shape = recipe.getShape();
        adaptedRecipe.shape(shape);
        for (char key : recipe.getIngredientMap().keySet()) {
            adaptedRecipe.setIngredient(key, recipe.getIngredientMap().get(key));
        }
        return adaptedRecipe;
    }

    public CraftShapelessRecipe fromBukkitShapelessRecipe(ShapelessRecipe recipe) {
        if (recipe instanceof CraftShapelessRecipe) {
            return (CraftShapelessRecipe) recipe;
        }

        CraftShapelessRecipe adaptedRecipe = new CraftShapelessRecipe(recipe.getResult());
        for (MaterialData ingredient : recipe.getIngredientList()) {
            adaptedRecipe.addIngredient(ingredient);
        }
        return adaptedRecipe;
    }
}
