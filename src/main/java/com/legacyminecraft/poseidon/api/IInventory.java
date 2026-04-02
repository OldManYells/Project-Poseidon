package com.legacyminecraft.poseidon.api;

import com.legacyminecraft.poseidon.world.entity.EntityHuman;
import com.legacyminecraft.poseidon.world.item.ItemStack;

public interface IInventory {

    int getSize();

    ItemStack getItem(int i);

    ItemStack splitStack(int i, int j);

    void setItem(int i, ItemStack itemstack);

    String getName();

    int getMaxStackSize();

    void update();

    boolean a_(EntityHuman entityhuman);

    ItemStack[] getContents(); // CraftBukkit
}
