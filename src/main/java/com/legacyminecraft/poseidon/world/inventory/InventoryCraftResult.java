package com.legacyminecraft.poseidon.world.inventory;

import com.legacyminecraft.poseidon.api.IInventory;
import com.legacyminecraft.poseidon.world.entity.EntityHuman;
import com.legacyminecraft.poseidon.world.item.ItemStack;

public class InventoryCraftResult implements IInventory {

    private ItemStack[] items = new ItemStack[1];

    public ItemStack[] getContents() {
        return this.items;
    }

    public InventoryCraftResult() {}

    public int getSize() {
        return 1;
    }

    public ItemStack getItem(int i) {
        return this.items[i];
    }

    public String getName() {
        return "Result";
    }

    public ItemStack splitStack(int i, int j) {
        if (this.items[i] != null) {
            ItemStack itemstack = this.items[i];

            this.items[i] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    public void setItem(int i, ItemStack itemstack) {
        this.items[i] = itemstack;
    }

    public int getMaxStackSize() {
        return 64;
    }

    public void update() {}

    public boolean a_(EntityHuman entityhuman) {
        return true;
    }
}
