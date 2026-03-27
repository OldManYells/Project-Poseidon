package com.legacyminecraft.compat.bukkit;

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

    public org.bukkit.inventory.Inventory getInventory(net.minecraft.server.Slot slot) {
        return new org.bukkit.craftbukkit.inventory.CraftInventory(slot.inventory);
    }

    public int getIndex(net.minecraft.server.Slot slot) {
        return slot.index;
    }

    public org.bukkit.inventory.ItemStack getItem(net.minecraft.server.Slot slot) {
        return new org.bukkit.craftbukkit.inventory.CraftItemStack(slot.getItem());
    }
}
