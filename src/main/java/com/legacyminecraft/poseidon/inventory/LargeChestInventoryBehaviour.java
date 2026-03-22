package com.legacyminecraft.poseidon.inventory;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.IInventory;
import net.minecraft.server.ItemStack;

/**
 * Canonical operations for combined two-inventory chest views.
 */
public final class LargeChestInventoryBehaviour {
    private static final LargeChestInventoryBehaviour INSTANCE = new LargeChestInventoryBehaviour();

    private LargeChestInventoryBehaviour() {
    }

    public static LargeChestInventoryBehaviour getInstance() {
        return INSTANCE;
    }

    public int getSize(IInventory left, IInventory right) {
        return left.getSize() + right.getSize();
    }

    public ItemStack getItem(IInventory left, IInventory right, int index) {
        int leftSize = left.getSize();
        return index >= leftSize ? right.getItem(index - leftSize) : left.getItem(index);
    }

    public ItemStack splitStack(IInventory left, IInventory right, int index, int amount) {
        int leftSize = left.getSize();
        return index >= leftSize ? right.splitStack(index - leftSize, amount) : left.splitStack(index, amount);
    }

    public void setItem(IInventory left, IInventory right, int index, ItemStack itemStack) {
        int leftSize = left.getSize();
        if (index >= leftSize) {
            right.setItem(index - leftSize, itemStack);
        } else {
            left.setItem(index, itemStack);
        }
    }

    public ItemStack[] getContents(IInventory left, IInventory right) {
        ItemStack[] result = new ItemStack[getSize(left, right)];
        for (int i = 0; i < result.length; i++) {
            result[i] = getItem(left, right, i);
        }
        return result;
    }

    public void update(IInventory left, IInventory right) {
        left.update();
        right.update();
    }

    public boolean canUse(IInventory left, IInventory right, EntityHuman entityHuman) {
        return left.a_(entityHuman) && right.a_(entityHuman);
    }
}
