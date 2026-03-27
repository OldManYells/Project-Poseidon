package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local crafting result inventory scaffold.
 */
public class InventoryCraftResult implements IInventory {
    private ItemStack itemStack;

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public ItemStack getItem(int index) {
        return index == 0 ? itemStack : null;
    }

    @Override
    public ItemStack splitStack(int index, int amount) {
        if (index != 0 || itemStack == null) {
            return null;
        }
        ItemStack split = itemStack;
        itemStack = null;
        return split;
    }

    @Override
    public void setItem(int index, ItemStack itemStack) {
        if (index == 0) {
            this.itemStack = itemStack;
        }
    }

    @Override
    public String getName() {
        return "Result";
    }

    @Override
    public int getMaxStackSize() {
        return 64;
    }

    @Override
    public void update() {
    }

    @Override
    public boolean canPlayerUse(EntityHuman entityHuman) {
        return true;
    }

    @Override
    public ItemStack[] getContents() {
        return new ItemStack[] { itemStack };
    }
}
