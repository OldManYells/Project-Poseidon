package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local set-slot packet alias.
 */
public class Packet103SetSlot extends com.legacyminecraft.compat.bukkit.Packet103SetSlot {
    public Packet103SetSlot(int windowId, int slot, ItemStack stack) {
        super(windowId, slot, stack);
    }
}
