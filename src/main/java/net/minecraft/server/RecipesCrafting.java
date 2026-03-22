package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.CraftingBlockRecipeRegistrationBehaviour;

public class RecipesCrafting {
    private static final CraftingBlockRecipeRegistrationBehaviour CRAFTING_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR = CraftingBlockRecipeRegistrationBehaviour.getInstance();

    public RecipesCrafting() {}

    public void a(CraftingManager craftingmanager) {
        CRAFTING_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR.registerAll(craftingmanager);
    }
}
