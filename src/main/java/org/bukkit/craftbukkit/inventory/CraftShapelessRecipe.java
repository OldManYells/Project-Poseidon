package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.compat.bukkit.RecipeAdapterBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.RecipeRegistrationOrchestrationBehaviour;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;

public class CraftShapelessRecipe extends ShapelessRecipe implements CraftRecipe {
    private static final RecipeAdapterBridgeBehaviour RECIPE_ADAPTER_BRIDGE_BEHAVIOUR =
            RecipeAdapterBridgeBehaviour.getInstance();
    private static final RecipeRegistrationOrchestrationBehaviour RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR =
            RecipeRegistrationOrchestrationBehaviour.getInstance();

    public CraftShapelessRecipe(ItemStack result) {
        super(result);
    }

    public static CraftShapelessRecipe fromBukkitRecipe(ShapelessRecipe recipe) {
        return RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitShapelessRecipe(recipe);
    }

    public void addToCraftingManager() {
        RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR.registerShapelessRecipe(this);
    }
}
