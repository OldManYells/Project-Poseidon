package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.compat.bukkit.RecipeAdapterBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.RecipeRegistrationOrchestrationBehaviour;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class CraftFurnaceRecipe extends FurnaceRecipe implements CraftRecipe {
    private static final RecipeAdapterBridgeBehaviour RECIPE_ADAPTER_BRIDGE_BEHAVIOUR =
            RecipeAdapterBridgeBehaviour.getInstance();
    private static final RecipeRegistrationOrchestrationBehaviour RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR =
            RecipeRegistrationOrchestrationBehaviour.getInstance();

    public CraftFurnaceRecipe(ItemStack result, Material source) {
        super(result, source);
    }

    public CraftFurnaceRecipe(ItemStack result, MaterialData source) {
        super(result, source);
    }

    public static CraftFurnaceRecipe fromBukkitRecipe(FurnaceRecipe recipe) {
        return RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitFurnaceRecipe(recipe);
    }

    public void addToCraftingManager() {
        RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR.registerFurnaceRecipe(this);
    }
}
