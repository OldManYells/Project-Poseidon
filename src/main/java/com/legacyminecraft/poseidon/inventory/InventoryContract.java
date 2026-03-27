package com.legacyminecraft.poseidon.inventory;

/**
 * Canonical inventory contract bridged by legacy inventory wrappers.
 */
public interface InventoryContract {
    int getSize();

    Object getItem(int slot);

    Object splitStack(int slot, int amount);

    void setItem(int slot, Object stack);

    String getName();

    int getMaxStackSize();

    void update();

    boolean canPlayerUse(Object player);

    Object[] getContents();
}
