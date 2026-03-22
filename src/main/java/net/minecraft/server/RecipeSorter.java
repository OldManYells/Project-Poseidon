package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.RecipeOrderingBehaviour;

import java.util.Comparator;

class RecipeSorter implements Comparator {
    private static final RecipeOrderingBehaviour RECIPE_ORDERING_BEHAVIOUR = RecipeOrderingBehaviour.getInstance();

    final CraftingManager a;

    RecipeSorter(CraftingManager craftingmanager) {
        this.a = craftingmanager;
    }

    public int compare(Object o1, Object o2) {
        return RECIPE_ORDERING_BEHAVIOUR.compare((CraftingRecipe) o1, (CraftingRecipe) o2);
    }
}
