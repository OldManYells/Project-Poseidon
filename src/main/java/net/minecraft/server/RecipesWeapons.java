package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.EquipmentRecipeRegistrationBehaviour;

public class RecipesWeapons {
    private static final EquipmentRecipeRegistrationBehaviour EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR = EquipmentRecipeRegistrationBehaviour.getInstance();

    private String[][] a = EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createWeaponPatterns();
    private Object[][] b;

    public RecipesWeapons() {
        this.b = EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.createWeaponTable();
    }

    public void a(CraftingManager craftingmanager) {
        EQUIPMENT_RECIPE_REGISTRATION_BEHAVIOUR.registerWeapons(craftingmanager, this.a, this.b);
    }
}
