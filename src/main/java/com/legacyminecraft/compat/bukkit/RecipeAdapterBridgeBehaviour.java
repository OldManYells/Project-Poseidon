package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for adapting Bukkit recipe types into CraftBukkit recipe wrappers.
 */
public final class RecipeAdapterBridgeBehaviour {
    private static final RecipeAdapterBridgeBehaviour INSTANCE = new RecipeAdapterBridgeBehaviour();
    private static final RecipeWrapperProjectionBridgeBehaviour RECIPE_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            RecipeWrapperProjectionBridgeBehaviour.getInstance();

    private RecipeAdapterBridgeBehaviour() {
    }

    public static RecipeAdapterBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public CraftFurnaceRecipe fromBukkitFurnaceRecipe(FurnaceRecipe recipe) {
        CraftFurnaceRecipe craftRecipe = RECIPE_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftFurnaceRecipe(recipe);
        if (craftRecipe != null) {
            return craftRecipe;
        }
        return new CraftFurnaceRecipe(recipe.getResult(), recipe.getInput());
    }

    public CraftShapedRecipe fromBukkitShapedRecipe(ShapedRecipe recipe) {
        CraftShapedRecipe craftRecipe = RECIPE_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftShapedRecipe(recipe);
        if (craftRecipe != null) {
            return craftRecipe;
        }

        CraftShapedRecipe adaptedRecipe = new CraftShapedRecipe(recipe.getResult());
        String[] shape = recipe.getShape();
        adaptedRecipe.shape(shape);
        for (char key : recipe.getIngredientMap().keySet()) {
            adaptedRecipe.setIngredient(key, recipe.getIngredientMap().get(key));
        }
        return adaptedRecipe;
    }

    public CraftShapelessRecipe fromBukkitShapelessRecipe(ShapelessRecipe recipe) {
        CraftShapelessRecipe craftRecipe = RECIPE_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftShapelessRecipe(recipe);
        if (craftRecipe != null) {
            return craftRecipe;
        }

        CraftShapelessRecipe adaptedRecipe = new CraftShapelessRecipe(recipe.getResult());
        for (MaterialData ingredient : recipe.getIngredientList()) {
            adaptedRecipe.addIngredient(ingredient);
        }
        return adaptedRecipe;
    }
}
