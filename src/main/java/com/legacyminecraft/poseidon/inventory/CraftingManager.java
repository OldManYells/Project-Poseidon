package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local crafting-manager scaffold.
 */
public class CraftingManager {
    private static final CraftingManager INSTANCE = new CraftingManager();

    public static CraftingManager getInstance() {
        return INSTANCE;
    }

    public void registerShapedRecipe(ItemStack itemStack, Object... recipeDefinition) {
        // Thin bridge for legacy callers; concrete registration lives on the NMS wrapper.
    }

    public void registerShapelessRecipe(ItemStack itemStack, Object... ingredients) {
        // Thin bridge for legacy callers; concrete registration lives on the NMS wrapper.
    }

    public ItemStack craft(InventoryCrafting inventoryCrafting) {
        return null;
    }
}
