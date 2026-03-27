package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local shapeless recipe scaffold.
 */
public class ShapelessRecipes implements CraftingRecipe {
    @Override
    public boolean matches(InventoryCrafting inventoryCrafting) {
        return false;
    }

    @Override
    public ItemStack craft(InventoryCrafting inventoryCrafting) {
        return null;
    }

    @Override
    public int ingredientCount() {
        return 0;
    }

    @Override
    public ItemStack result() {
        return null;
    }

    @Override
    public int a() {
        return ingredientCount();
    }
}
