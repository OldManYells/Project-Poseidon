package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftRecipeWrapperThinnessTest {
    private static final Path CRAFT_FURNACE_RECIPE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/inventory/CraftFurnaceRecipe.java");
    private static final Path CRAFT_SHAPED_RECIPE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/inventory/CraftShapedRecipe.java");
    private static final Path CRAFT_SHAPELESS_RECIPE_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/inventory/CraftShapelessRecipe.java");
    private static final Path RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/RecipeRegistrationOrchestrationBehaviour.java");
    private static final Path RECIPE_ADAPTER_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/RecipeAdapterBridgeBehaviour.java");

    @Test
    public void craftFurnaceRecipeDelegatesRegistrationConversionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_FURNACE_RECIPE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeAdapterBridgeBehaviour"));
        Assert.assertTrue(text.contains("fromBukkitFurnaceRecipe(recipe)"));
        Assert.assertTrue(text.contains("RecipeRegistrationOrchestrationBehaviour"));
        Assert.assertTrue(text.contains("registerFurnaceRecipe(this)"));
        Assert.assertFalse(text.contains("FurnaceRecipes.getInstance().registerRecipe("));
        Assert.assertFalse(text.contains("if (recipe instanceof CraftFurnaceRecipe)"));
        Assert.assertFalse(text.contains("new net.minecraft.server.ItemStack(id, amount, dmg)"));
    }

    @Test
    public void craftShapedRecipeDelegatesRegistrationConversionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SHAPED_RECIPE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeAdapterBridgeBehaviour"));
        Assert.assertTrue(text.contains("fromBukkitShapedRecipe(recipe)"));
        Assert.assertTrue(text.contains("RecipeRegistrationOrchestrationBehaviour"));
        Assert.assertTrue(text.contains("registerShapedRecipe(this)"));
        Assert.assertFalse(text.contains("CraftingManager.getInstance().registerShapedRecipe("));
        Assert.assertFalse(text.contains("CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getResult());"));
        Assert.assertFalse(text.contains("data[i] = new net.minecraft.server.ItemStack(id, 1, dmg);"));
    }

    @Test
    public void craftShapelessRecipeDelegatesRegistrationConversionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SHAPELESS_RECIPE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeAdapterBridgeBehaviour"));
        Assert.assertTrue(text.contains("fromBukkitShapelessRecipe(recipe)"));
        Assert.assertTrue(text.contains("RecipeRegistrationOrchestrationBehaviour"));
        Assert.assertTrue(text.contains("registerShapelessRecipe(this)"));
        Assert.assertFalse(text.contains("CraftingManager.getInstance().registerShapelessRecipe("));
        Assert.assertFalse(text.contains("CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getResult());"));
        Assert.assertFalse(text.contains("Object[] data = new Object[ingred.size()];"));
        Assert.assertFalse(text.contains("data[i] = new net.minecraft.server.ItemStack(id, 1, dmg);"));
    }

    @Test
    public void recipeRegistrationOrchestrationBehaviourOwnsManagerInvocationAndConversionPolicy() throws IOException {
        String text = new String(Files.readAllBytes(RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeRegistrationBridgeBehaviour"));
        Assert.assertTrue(text.contains("registerFurnaceRecipe(CraftFurnaceRecipe recipe)"));
        Assert.assertTrue(text.contains("registerShapedRecipe(CraftShapedRecipe recipe)"));
        Assert.assertTrue(text.contains("registerShapelessRecipe(CraftShapelessRecipe recipe)"));
        Assert.assertTrue(text.contains("FurnaceRecipes.getInstance().registerRecipe("));
        Assert.assertTrue(text.contains("CraftingManager.getInstance().registerShapedRecipe("));
        Assert.assertTrue(text.contains("CraftingManager.getInstance().registerShapelessRecipe("));
        Assert.assertTrue(text.contains("toNmsResult(recipe.getResult())"));
        Assert.assertTrue(text.contains("toShapedData(shape, ingred)"));
        Assert.assertTrue(text.contains("toShapelessData(ingred)"));
        Assert.assertTrue(text.contains("resolveCraftRecipe(recipe)"));
        Assert.assertFalse(text.contains("if (recipe instanceof CraftRecipe)"));
    }

    @Test
    public void recipeAdapterBridgeBehaviourOwnsCraftRecipeProjectionPolicy() throws IOException {
        String text = new String(Files.readAllBytes(RECIPE_ADAPTER_BRIDGE_BEHAVIOUR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeWrapperProjectionBridgeBehaviour"));
        Assert.assertTrue(text.contains("resolveCraftFurnaceRecipe(recipe)"));
        Assert.assertTrue(text.contains("resolveCraftShapedRecipe(recipe)"));
        Assert.assertTrue(text.contains("resolveCraftShapelessRecipe(recipe)"));
        Assert.assertFalse(text.contains("if (recipe instanceof CraftFurnaceRecipe)"));
        Assert.assertFalse(text.contains("if (recipe instanceof CraftShapedRecipe)"));
        Assert.assertFalse(text.contains("if (recipe instanceof CraftShapelessRecipe)"));
        Assert.assertFalse(text.contains("return (CraftFurnaceRecipe) recipe;"));
        Assert.assertFalse(text.contains("return (CraftShapedRecipe) recipe;"));
        Assert.assertFalse(text.contains("return (CraftShapelessRecipe) recipe;"));
    }
}
