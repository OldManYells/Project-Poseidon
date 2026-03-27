package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local chest tile-entity scaffold.
 */
public class TileEntityChest extends com.legacyminecraft.poseidon.world.TileEntity {
    private final ItemStack[] items = new ItemStack[27];

    public int getSize() {
        return items.length;
    }

    public ItemStack getItem(int index) {
        return index >= 0 && index < items.length ? items[index] : null;
    }

    public void setItem(int index, ItemStack stack) {
        if (index >= 0 && index < items.length) {
            items[index] = stack;
        }
    }
}
