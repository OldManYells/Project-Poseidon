package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.FoodRecipeRegistrationBehaviour;

public class RecipesFood {
    private static final FoodRecipeRegistrationBehaviour FOOD_RECIPE_REGISTRATION_BEHAVIOUR = FoodRecipeRegistrationBehaviour.getInstance();

    public RecipesFood() {}

    public void a(CraftingManager craftingmanager) {
        FOOD_RECIPE_REGISTRATION_BEHAVIOUR.registerAll(craftingmanager);
    }
}
