package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.recipe.CraftingRecipeContract;

public interface CraftingRecipe extends CraftingRecipeContract {

    boolean a(InventoryCrafting inventorycrafting);

    ItemStack b(InventoryCrafting inventorycrafting);

    int a();

    ItemStack b();

    default boolean matches(InventoryCrafting inventoryCrafting) {
        return this.a(inventoryCrafting);
    }

    default ItemStack craft(InventoryCrafting inventoryCrafting) {
        return this.b(inventoryCrafting);
    }

    default int ingredientCount() {
        return this.a();
    }

    default ItemStack result() {
        return this.b();
    }
}
