package com.legacyminecraft.compat.bukkit;

import java.util.HashMap;

/**
 * Canonical compat shaped-recipe scaffold.
 */
public class ShapedRecipe implements Recipe {
    private final ItemStack result;
    private String[] shape = new String[0];
    private final HashMap<Character, MaterialData> ingredientMap = new HashMap<Character, MaterialData>();

    public ShapedRecipe(ItemStack result) {
        this.result = result;
    }

    @Override
    public ItemStack getResult() {
        return result;
    }

    public ShapedRecipe shape(String... shape) {
        this.shape = shape == null ? new String[0] : shape;
        return this;
    }

    public String[] getShape() {
        return shape;
    }

    public HashMap<Character, MaterialData> getIngredientMap() {
        return ingredientMap;
    }

    public ShapedRecipe setIngredient(char key, MaterialData ingredient) {
        ingredientMap.put(Character.valueOf(key), ingredient);
        return this;
    }
}
