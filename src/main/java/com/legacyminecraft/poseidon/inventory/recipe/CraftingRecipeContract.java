package com.legacyminecraft.poseidon.inventory.recipe;

import com.legacyminecraft.poseidon.inventory.InventoryCrafting;
import com.legacyminecraft.poseidon.inventory.ItemStack;

/**
 * Canonical recipe contract bridged by legacy crafting recipe wrappers.
 */
public interface CraftingRecipeContract {
    boolean matches(InventoryCrafting inventoryCrafting);

    ItemStack craft(InventoryCrafting inventoryCrafting);

    int ingredientCount();

    ItemStack result();
}
