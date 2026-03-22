package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.DyeRecipeRegistrationBehaviour;

public class RecipesDyes {
    private static final DyeRecipeRegistrationBehaviour DYE_RECIPE_REGISTRATION_BEHAVIOUR = DyeRecipeRegistrationBehaviour.getInstance();

    public RecipesDyes() {}

    public void a(CraftingManager craftingmanager) {
        DYE_RECIPE_REGISTRATION_BEHAVIOUR.registerAll(craftingmanager);
    }
}
