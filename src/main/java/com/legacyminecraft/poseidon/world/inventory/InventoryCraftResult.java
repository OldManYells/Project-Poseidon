package com.legacyminecraft.poseidon.world.inventory;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;
import com.legacyminecraft.poseidon.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.item.*;
import com.legacyminecraft.poseidon.world.block.*;
import com.legacyminecraft.poseidon.world.entity.*;
import com.legacyminecraft.poseidon.world.item.*;

public class InventoryCraftResult implements IInventory {

    private ItemStack[] items = new ItemStack[1];

    // CraftBukkit start
    public ItemStack[] getContents() {
        return this.items;
    }
    // CraftBukkit end

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
