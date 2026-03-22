package com.legacyminecraft.poseidon.inventory.recipe;

import net.minecraft.server.InventoryCrafting;
import net.minecraft.server.ItemStack;

/**
 * Canonical recipe contract bridged by legacy crafting recipe wrappers.
 */
public interface CraftingRecipeContract {
    boolean matches(InventoryCrafting inventoryCrafting);

    ItemStack craft(InventoryCrafting inventoryCrafting);

    int ingredientCount();

    ItemStack result();
}
