package com.legacyminecraft.poseidon;
import com.legacyminecraft.poseidon.world.item.*;

public interface CraftingRecipe {

    boolean a(InventoryCrafting inventorycrafting);

    ItemStack b(InventoryCrafting inventorycrafting);

    int a();

    ItemStack b();
}
