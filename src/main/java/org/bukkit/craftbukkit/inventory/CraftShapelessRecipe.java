package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.poseidon.compat.bukkit.RecipeAdapterBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.RecipeRegistrationBridgeBehaviour;
import net.minecraft.server.CraftingManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.material.MaterialData;

import java.util.ArrayList;

public class CraftShapelessRecipe extends ShapelessRecipe implements CraftRecipe {
    private static final RecipeAdapterBridgeBehaviour RECIPE_ADAPTER_BRIDGE_BEHAVIOUR =
            RecipeAdapterBridgeBehaviour.getInstance();
    private static final RecipeRegistrationBridgeBehaviour RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR =
            RecipeRegistrationBridgeBehaviour.getInstance();

    public CraftShapelessRecipe(ItemStack result) {
        super(result);
    }

    public static CraftShapelessRecipe fromBukkitRecipe(ShapelessRecipe recipe) {
        return RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitShapelessRecipe(recipe);
    }

    public void addToCraftingManager() {
        ArrayList<MaterialData> ingred = this.getIngredientList();
        Object[] data = RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toShapelessData(ingred);
        CraftingManager.getInstance().registerShapelessRecipe(
                RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toNmsResult(this.getResult()),
                data
        );
    }
}
