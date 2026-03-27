package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Canonical behaviour for CraftBukkit recipe registration item/data conversion.
 */
public final class RecipeRegistrationBridgeBehaviour {
    private static final RecipeRegistrationBridgeBehaviour INSTANCE = new RecipeRegistrationBridgeBehaviour();

    private RecipeRegistrationBridgeBehaviour() {
    }

    public static RecipeRegistrationBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public com.legacyminecraft.compat.bukkit.ItemStack toNmsResult(ItemStack result) {
        return new com.legacyminecraft.compat.bukkit.ItemStack(result.getTypeId(), result.getAmount(), result.getDurability());
    }

    public com.legacyminecraft.compat.bukkit.ItemStack toNmsIngredient(MaterialData materialData) {
        return new com.legacyminecraft.compat.bukkit.ItemStack(materialData.getItemTypeId(), 1, materialData.getData());
    }

    public Object[] toShapedData(String[] shape, HashMap<Character, MaterialData> ingredients) {
        int dataLength = shape.length + ingredients.size() * 2;
        Object[] data = new Object[dataLength];
        int index = 0;

        for (; index < shape.length; index++) {
            data[index] = shape[index];
        }

        for (char ingredientKey : ingredients.keySet()) {
            data[index] = ingredientKey;
            index++;
            data[index] = toNmsIngredient(ingredients.get(ingredientKey));
            index++;
        }

        return data;
    }

    public Object[] toShapelessData(ArrayList<MaterialData> ingredients) {
        Object[] data = new Object[ingredients.size()];
        int index = 0;
        for (MaterialData ingredient : ingredients) {
            data[index] = toNmsIngredient(ingredient);
            index++;
        }
        return data;
    }
}
