package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.EquipmentRecipeRegistrationBehaviour;

public class RecipesTools {
    private static final EquipmentRecipeRegistrationBehaviour EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR = EquipmentRecipeRegistrationBehaviour.getInstance();

    private String[][] a = EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createToolPatterns();
    private Object[][] b;

    public RecipesTools() {
        this.b = EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createToolTable();
    }

    public void a(CraftingManager craftingmanager) {
        EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.registerTools(craftingmanager, this.a, this.b);
    }
}
