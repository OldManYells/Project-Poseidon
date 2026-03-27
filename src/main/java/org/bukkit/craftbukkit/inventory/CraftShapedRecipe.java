package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.compat.bukkit.RecipeAdapterBridgeBehaviour;
import com.legacyminecraft.compat.bukkit.RecipeRegistrationOrchestrationBehaviour;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe {
    private static final RecipeAdapterBridgeBehaviour RECIPE_ADAPTER_BRIDGE_BEHAVIOUR =
            RecipeAdapterBridgeBehaviour.getInstance();
    private static final RecipeRegistrationOrchestrationBehaviour RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR =
            RecipeRegistrationOrchestrationBehaviour.getInstance();

    public CraftShapedRecipe(ItemStack result) {
        super(result);
    }

    public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
        return RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitShapedRecipe(recipe);
    }

    public void addToCraftingManager() {
        RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR.registerShapedRecipe(this);
    }
}
