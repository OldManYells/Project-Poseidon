package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.ShapedRecipeMatchingBehaviour;

public class ShapedRecipes implements CraftingRecipe {
    private static final ShapedRecipeMatchingBehaviour SHAPED_RECIPE_MATCHING_BEHAVIOUR = ShapedRecipeMatchingBehaviour.getInstance();

    private int b;
    private int c;
    private ItemStack[] d;
    private ItemStack e;
    public final int a;

    public ShapedRecipes(int i, int j, ItemStack[] aitemstack, ItemStack itemstack) {
        this.a = itemstack.id;
        this.b = i;
        this.c = j;
        this.d = aitemstack;
        this.e = itemstack;
    }

    public ItemStack b() {
        return this.e;
    }

    public boolean a(InventoryCrafting inventorycrafting) {
        return SHAPED_RECIPE_MATCHING_BEHAVIOUR.matches(this.b, this.c, this.d, inventorycrafting);
    }

    public ItemStack b(InventoryCrafting inventorycrafting) {
        return SHAPED_RECIPE_MATCHING_BEHAVIOUR.craftResult(this.e);
    }

    public int a() {
        return SHAPED_RECIPE_MATCHING_BEHAVIOUR.ingredientCount(this.b, this.c);
    }
}
