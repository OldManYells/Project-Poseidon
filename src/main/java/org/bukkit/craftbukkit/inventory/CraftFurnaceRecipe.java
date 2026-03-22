package org.bukkit.craftbukkit.inventory;

import com.legacyminecraft.poseidon.compat.bukkit.RecipeAdapterBridgeBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.RecipeRegistrationBridgeBehaviour;
import net.minecraft.server.FurnaceRecipes;
import org.bukkit.Material;
import org.bukkit.inventory.FurnaceRecipe;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public class CraftFurnaceRecipe extends FurnaceRecipe implements CraftRecipe {
    private static final RecipeAdapterBridgeBehaviour RECIPE_ADAPTER_BRIDGE_BEHAVIOUR =
            RecipeAdapterBridgeBehaviour.getInstance();
    private static final RecipeRegistrationBridgeBehaviour RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR =
            RecipeRegistrationBridgeBehaviour.getInstance();

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
        MaterialData input = this.getInput();
        FurnaceRecipes.getInstance().registerRecipe(
                input.getItemTypeId(),
                RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toNmsResult(this.getResult())
        );
    }
}
