package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat inventory contract.
 */
public interface IInventory extends Inventory {
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
