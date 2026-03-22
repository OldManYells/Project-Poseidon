package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.ItemStack;

/**
 * Canonical inventory-grid operations for legacy InventoryCrafting wrappers.
 */
public final class CraftingInventoryBehaviour {
    private static final CraftingInventoryBehaviour INSTANCE = new CraftingInventoryBehaviour();

    private CraftingInventoryBehaviour() {
    }

    public static CraftingInventoryBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack[] createGridStorage(int width, int height) {
        return new ItemStack[width * height];
    }

    public ItemStack getByIndex(ItemStack[] items, int size, int index) {
        return index >= size ? null : items[index];
    }

    public ItemStack getByGridPosition(ItemStack[] items, int width, int x, int y) {
        if (x < 0 || x >= width) {
            return null;
        }
        int index = x + y * width;
        return getByIndex(items, items.length, index);
    }

    public SplitResult split(ItemStack[] items, int index, int amount) {
        if (items[index] == null) {
            return SplitResult.unchanged(null);
        }

        ItemStack result;
        if (items[index].count <= amount) {
            result = items[index];
            items[index] = null;
            return SplitResult.changed(result);
        }

        result = items[index].a(amount);
        if (items[index].count == 0) {
            items[index] = null;
        }
        return SplitResult.changed(result);
    }

    public void set(ItemStack[] items, int index, ItemStack itemStack) {
        items[index] = itemStack;
    }

    public static final class SplitResult {
        private final boolean changed;
        private final ItemStack itemStack;

        private SplitResult(boolean changed, ItemStack itemStack) {
            this.changed = changed;
            this.itemStack = itemStack;
        }

        public static SplitResult changed(ItemStack itemStack) {
            return new SplitResult(true, itemStack);
        }

        public static SplitResult unchanged(ItemStack itemStack) {
            return new SplitResult(false, itemStack);
        }

        public boolean isChanged() {
            return changed;
        }

        public ItemStack getItemStack() {
            return itemStack;
        }
    }
}
