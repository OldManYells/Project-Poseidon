package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local contract alias.
 */
public interface IInventory {
    int getSize();

    ItemStack getItem(int index);

    ItemStack splitStack(int index, int amount);

    void setItem(int index, ItemStack itemStack);

    String getName();

    int getMaxStackSize();

    void update();

    boolean canPlayerUse(EntityHuman entityHuman);

    ItemStack[] getContents();
}
