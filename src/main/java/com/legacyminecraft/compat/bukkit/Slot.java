package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat slot alias.
 */
public class Slot extends com.legacyminecraft.poseidon.inventory.Slot {
    public final com.legacyminecraft.poseidon.inventory.IInventory inventory;
    public final int index;

    public Slot() {
        this(new com.legacyminecraft.poseidon.inventory.InventoryCraftResult(), 0, 0, 0);
    }

    public Slot(com.legacyminecraft.poseidon.inventory.IInventory inventory, int index, int x, int y) {
        super(inventory, index, x, y);
        this.inventory = inventory;
        this.index = index;
    }
}
