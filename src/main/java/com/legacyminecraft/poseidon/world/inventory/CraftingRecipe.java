package com.legacyminecraft.poseidon.world.inventory;

import com.legacyminecraft.poseidon.world.item.ItemStack;

public interface CraftingRecipe {

    boolean a(InventoryCrafting inventorycrafting);

    ItemStack b(InventoryCrafting inventorycrafting);

    int a();

    ItemStack b();
}
