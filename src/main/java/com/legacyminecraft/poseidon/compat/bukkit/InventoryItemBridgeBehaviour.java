package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.inventory.ItemStack;
import org.bukkit.craftbukkit.inventory.CraftItemStack;

/**
 * Canonical behaviour for CraftInventory item conversion/copy bridge policy.
 */
public final class InventoryItemBridgeBehaviour {
    private static final InventoryItemBridgeBehaviour INSTANCE = new InventoryItemBridgeBehaviour();

    private InventoryItemBridgeBehaviour() {
    }

    public static InventoryItemBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack wrapSingle(net.minecraft.server.ItemStack item) {
        return new CraftItemStack(item);
    }

    public ItemStack[] toBukkitContents(net.minecraft.server.ItemStack[] source, int inventorySize) {
        ItemStack[] items = new ItemStack[inventorySize];
        for (int index = 0; index < source.length; index++) {
            net.minecraft.server.ItemStack item = source[index];
            items[index] = item == null ? null : new CraftItemStack(item);
        }
        return items;
    }

    public void copyContentsToNms(ItemStack[] source, net.minecraft.server.ItemStack[] target) {
        for (int index = 0; index < source.length; index++) {
            target[index] = toNmsForContentsSlot(source[index]);
        }
    }

    public net.minecraft.server.ItemStack toNmsForSetItem(ItemStack item) {
        return item == null ? null : new net.minecraft.server.ItemStack(item.getTypeId(), item.getAmount(), item.getDurability());
    }

    private net.minecraft.server.ItemStack toNmsForContentsSlot(ItemStack item) {
        if (item == null || item.getTypeId() <= 0) {
            return null;
        }
        return new net.minecraft.server.ItemStack(item.getTypeId(), item.getAmount(), item.getDurability());
    }
}
