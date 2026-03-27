package com.legacyminecraft.poseidon.inventory;


public final class RecipeOrderingBehaviour {
    private static final RecipeOrderingBehaviour INSTANCE = new RecipeOrderingBehaviour();

    private RecipeOrderingBehaviour() {
    }

    public static RecipeOrderingBehaviour getInstance() {
        return INSTANCE;
    }

    public int compare(CraftingRecipe left, CraftingRecipe right) {
        if (left instanceof ShapelessRecipes && right instanceof ShapedRecipes) {
            return 1;
        }
        if (right instanceof ShapelessRecipes && left instanceof ShapedRecipes) {
            return -1;
        }
        if (right.a() < left.a()) {
            return -1;
        }
        if (right.a() > left.a()) {
            return 1;
        }
        return 0;
    }
}
