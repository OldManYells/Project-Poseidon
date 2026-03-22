package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.Slot;
import org.bukkit.craftbukkit.inventory.CraftInventory;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

/**
 * Canonical behaviour for CraftSlot inventory/item bridge operations.
 */
public final class InventorySlotBridgeBehaviour {
    private static final InventorySlotBridgeBehaviour INSTANCE = new InventorySlotBridgeBehaviour();

    private InventorySlotBridgeBehaviour() {
    }

    public static InventorySlotBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public Inventory getInventory(Slot slot) {
        return new CraftInventory(slot.inventory);
    }

    public ItemStack getItem(Slot slot) {
        return new CraftItemStack(slot.getItem());
    }
}
