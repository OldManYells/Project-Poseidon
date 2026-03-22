package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.ShapelessRecipeMatchingBehaviour;

import java.util.List;

public class ShapelessRecipes implements CraftingRecipe {
    private static final ShapelessRecipeMatchingBehaviour SHAPELESS_RECIPE_MATCHING_BEHAVIOUR = ShapelessRecipeMatchingBehaviour.getInstance();

    private final ItemStack a;
    private final List b;

    public ShapelessRecipes(ItemStack itemstack, List list) {
        this.a = itemstack;
        this.b = list;
    }

    public ItemStack b() {
        return this.a;
    }

    public boolean a(InventoryCrafting inventorycrafting) {
        return SHAPELESS_RECIPE_MATCHING_BEHAVIOUR.matches(this.b, inventorycrafting);
    }

    public ItemStack b(InventoryCrafting inventorycrafting) {
        return SHAPELESS_RECIPE_MATCHING_BEHAVIOUR.craftResult(this.a);
    }

    public int a() {
        return SHAPELESS_RECIPE_MATCHING_BEHAVIOUR.ingredientCount(this.b);
    }
}
