package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.IngotBlockRecipeRegistrationBehaviour;

public class RecipeIngots {
    private static final IngotBlockRecipeRegistrationBehaviour INGOT_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR = IngotBlockRecipeRegistrationBehaviour.getInstance();

    private Object[][] a;

    public RecipeIngots() {
        this.a = INGOT_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR.createRecipePairs();
    }

    public void a(CraftingManager craftingmanager) {
        INGOT_BLOCK_RECIPE_REGISTRATION_BEHAVIOUR.registerAll(craftingmanager, this.a);
    }
}
