package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Canonical behaviour for CraftBukkit recipe registration orchestration.
 */
public final class RecipeRegistrationOrchestrationBehaviour {
    private static final RecipeRegistrationOrchestrationBehaviour INSTANCE =
            new RecipeRegistrationOrchestrationBehaviour();
    private static final RecipeAdapterBridgeBehaviour RECIPE_ADAPTER_BRIDGE_BEHAVIOUR =
            RecipeAdapterBridgeBehaviour.getInstance();
    private static final RecipeWrapperProjectionBridgeBehaviour RECIPE_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            RecipeWrapperProjectionBridgeBehaviour.getInstance();
    private static final RecipeRegistrationBridgeBehaviour RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR =
            RecipeRegistrationBridgeBehaviour.getInstance();

    private RecipeRegistrationOrchestrationBehaviour() {
    }

    public static RecipeRegistrationOrchestrationBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean addRecipe(Recipe recipe) {
        CraftRecipe recipeToAdd = RECIPE_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftRecipe(recipe);
        if (recipeToAdd == null && recipe instanceof ShapedRecipe) {
            recipeToAdd = RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitShapedRecipe((ShapedRecipe) recipe);
        } else if (recipeToAdd == null && recipe instanceof ShapelessRecipe) {
            recipeToAdd = RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitShapelessRecipe((ShapelessRecipe) recipe);
        } else if (recipeToAdd == null && recipe instanceof FurnaceRecipe) {
            recipeToAdd = RECIPE_ADAPTER_BRIDGE_BEHAVIOUR.fromBukkitFurnaceRecipe((FurnaceRecipe) recipe);
        }

        if (recipeToAdd == null) {
            return false;
        }

        recipeToAdd.addToCraftingManager();
        return true;
    }

    public void registerFurnaceRecipe(CraftFurnaceRecipe recipe) {
        MaterialData input = recipe.getInput();
        FurnaceRecipes.getInstance().registerRecipe(
                input.getItemTypeId(),
                RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toNmsResult(recipe.getResult())
        );
    }

    public void registerShapedRecipe(CraftShapedRecipe recipe) {
        String[] shape = recipe.getShape();
        HashMap<Character, MaterialData> ingred = recipe.getIngredientMap();
        Object[] data = RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toShapedData(shape, ingred);
        CraftingManager.getInstance().registerShapedRecipe(
                RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toNmsResult(recipe.getResult()),
                data
        );
    }

    public void registerShapelessRecipe(CraftShapelessRecipe recipe) {
        ArrayList<MaterialData> ingred = recipe.getIngredientList();
        Object[] data = RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toShapelessData(ingred);
        CraftingManager.getInstance().registerShapelessRecipe(
                RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toNmsResult(recipe.getResult()),
                data
        );
    }
}
