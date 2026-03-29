package net.minecraft.server;

import org.bukkit.craftbukkit.item.ItemStack;

public interface CraftingRecipe {

    boolean a(InventoryCrafting inventorycrafting);

    ItemStack b(InventoryCrafting inventorycrafting);

    int a();

    ItemStack b();
}
