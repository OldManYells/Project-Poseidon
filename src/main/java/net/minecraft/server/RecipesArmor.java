package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.EquipmentRecipeRegistrationBehaviour;

public class RecipesArmor {
    private static final EquipmentRecipeRegistrationBehaviour EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR = EquipmentRecipeRegistrationBehaviour.getInstance();

    private String[][] a = EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createArmorPatterns();
    private Object[][] b;

    public RecipesArmor() {
        this.b = EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createArmorTable();
    }

    public void a(CraftingManager craftingmanager) {
        EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.registerArmor(craftingmanager, this.a, this.b);
    }
}
