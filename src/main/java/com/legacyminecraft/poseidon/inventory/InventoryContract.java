package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.ItemStack;

/**
 * Canonical inventory contract bridged by legacy inventory wrappers.
 */
public interface InventoryContract {
    int getSize();

    ItemStack getItem(int slot);

    ItemStack splitStack(int slot, int amount);

    void setItem(int slot, ItemStack stack);

    String getName();

    int getMaxStackSize();

    void update();

    boolean canPlayerUse(EntityHuman player);

    ItemStack[] getContents();
}
