package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

/**
 * Canonical single-slot crafting-result inventory operations.
 */
public final class CraftResultInventoryBehaviour {
    private static final CraftResultInventoryBehaviour INSTANCE = new CraftResultInventoryBehaviour();

    private CraftResultInventoryBehaviour() {
    }

    public static CraftResultInventoryBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack[] createStorage() {
        return new ItemStack[1];
    }

    public ItemStack get(ItemStack[] items, int index) {
        return items[index];
    }

    public ItemStack split(ItemStack[] items, int index) {
        if (items[index] == null) {
            return null;
        }

        ItemStack result = items[index];
        items[index] = null;
        return result;
    }

    public void set(ItemStack[] items, int index, ItemStack itemStack) {
        items[index] = itemStack;
    }
}
