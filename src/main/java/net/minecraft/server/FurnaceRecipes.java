package net.minecraft.server;

import com.legacyminecraft.poseidon.inventory.FurnaceRecipeRegistryBehaviour;

import java.util.HashMap;
import java.util.Map;

public class FurnaceRecipes {
    private static final FurnaceRecipeRegistryBehaviour FURNACE_RECIPE_REGISTRY_BEHAVIOUR = FurnaceRecipeRegistryBehaviour.getInstance();

    private static final FurnaceRecipes a = new FurnaceRecipes();
    private Map b = new HashMap();

    public static final FurnaceRecipes getInstance() {
        return a;
    }

    private FurnaceRecipes() {
        FURNACE_RECIPE_REGISTRY_BEHAVIOUR.registerDefaultRecipes(this.b);
    }

    public void registerRecipe(int i, ItemStack itemstack) {
        FURNACE_RECIPE_REGISTRY_BEHAVIOUR.registerRecipe(this.b, i, itemstack);
    }

    public ItemStack a(int i) {
        return FURNACE_RECIPE_REGISTRY_BEHAVIOUR.getRecipe(this.b, i);
    }

    public Map b() {
        return FURNACE_RECIPE_REGISTRY_BEHAVIOUR.getRecipes(this.b);
    }
}
