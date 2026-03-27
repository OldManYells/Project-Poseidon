package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;

/**
 * Canonical compat shapeless-recipe scaffold.
 */
public class ShapelessRecipe implements Recipe {
    private final ItemStack result;
    private final ArrayList<MaterialData> ingredients = new ArrayList<MaterialData>();

    public ShapelessRecipe(ItemStack result) {
        this.result = result;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    public ArrayList<MaterialData> getIngredientList() {
        return ingredients;
    }

    public ShapelessRecipe addIngredient(MaterialData ingredient) {
        ingredients.add(ingredient);
        return this;
    }
}
