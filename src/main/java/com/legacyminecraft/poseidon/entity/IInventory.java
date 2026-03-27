package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local inventory contract.
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
