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

    @Test
    public void craftFurnaceRecipeDelegatesRegistrationConversionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_FURNACE_RECIPE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeAdapterBridgeBehaviour"));
        Assert.assertTrue(text.contains("fromBukkitFurnaceRecipe(recipe)"));
        Assert.assertTrue(text.contains("RecipeRegistrationBridgeBehaviour"));
        Assert.assertTrue(text.contains("RECIPE_REGISTRATION_BRIDGE_BEHAVIOUR.toNmsResult(this.getResult())"));
        Assert.assertFalse(text.contains("if (recipe instanceof CraftFurnaceRecipe)"));
        Assert.assertFalse(text.contains("new net.minecraft.server.ItemStack(id, amount, dmg)"));
    }

    @Test
    public void craftShapedRecipeDelegatesRegistrationConversionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SHAPED_RECIPE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeAdapterBridgeBehaviour"));
        Assert.assertTrue(text.contains("fromBukkitShapedRecipe(recipe)"));
        Assert.assertTrue(text.contains("RecipeRegistrationBridgeBehaviour"));
        Assert.assertTrue(text.contains("toShapedData(shape, ingred)"));
        Assert.assertTrue(text.contains("toNmsResult(this.getResult())"));
        Assert.assertFalse(text.contains("CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getResult());"));
        Assert.assertFalse(text.contains("data[i] = new net.minecraft.server.ItemStack(id, 1, dmg);"));
    }

    @Test
    public void craftShapelessRecipeDelegatesRegistrationConversionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_SHAPELESS_RECIPE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("RecipeAdapterBridgeBehaviour"));
        Assert.assertTrue(text.contains("fromBukkitShapelessRecipe(recipe)"));
        Assert.assertTrue(text.contains("RecipeRegistrationBridgeBehaviour"));
        Assert.assertTrue(text.contains("toShapelessData(ingred)"));
        Assert.assertTrue(text.contains("toNmsResult(this.getResult())"));
        Assert.assertFalse(text.contains("CraftShapelessRecipe ret = new CraftShapelessRecipe(recipe.getResult());"));
        Assert.assertFalse(text.contains("Object[] data = new Object[ingred.size()];"));
        Assert.assertFalse(text.contains("data[i] = new net.minecraft.server.ItemStack(id, 1, dmg);"));
    }
}
