package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.poseidon.compat.bukkit.RecipeAdapterBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.RecipeRegistrationBridgeBehaviour;
import net.minecraft.server.CraftingManager;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.material.MaterialData;

import java.util.HashMap;

public class CraftShapedRecipe extends ShapedRecipe implements CraftRecipe {
    private static final RecipeAdapterBridgeBehaviour RECIPE_ADAPTER_BRIDGE_BEHAVIOUR =
            RecipeAdapterBridgeBehaviour.getInstance();
    private static final RecipeRegistrationBridgeBehaviour RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR =
            RecipeRegistrationBridgeBehaviour.getInstance();

    public CraftShapedRecipe(ItemStack result) {
        super(result);
    }

    public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
        return RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitShapedRecipe(recipe);
    }

    public void addToCraftingManager() {
        String[] shape = this.getShape();
        HashMap<Character, MaterialData> ingred = this.getIngredientMap();
        Object[] data = RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toShapedData(shape, ingred);
        CraftingManager.getInstance().registerShapedRecipe(
                RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toNmsResult(this.getResult()),
                data
        );
    }
}
