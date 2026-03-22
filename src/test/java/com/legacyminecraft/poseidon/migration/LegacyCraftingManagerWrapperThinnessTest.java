package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftingManagerWrapperThinnessTest {
    private static final Path CRAFTING_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/CraftingManager.java");

    @Test
    public void craftingManagerDelegatesBootstrapAndRecipeFlowToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFTING_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftingManagerBehaviour"));
        Assert.assertTrue(text.contains("CRAFTING_MANAGER_BEHAVIOUR.bootstrapDefaultRecipes"));
        Assert.assertTrue(text.contains("CRAFTING_MANAGER_BEHAVIOUR.registerShapedRecipe"));
        Assert.assertTrue(text.contains("CRAFTING_MANAGER_BEHAVIOUR.registerShapelessRecipe"));
        Assert.assertTrue(text.contains("CRAFTING_MANAGER_BEHAVIOUR.craft"));
        Assert.assertTrue(text.contains("CRAFTING_MANAGER_BEHAVIOUR.recipes"));
        Assert.assertFalse(text.contains("(new RecipesTools()).a(this);"));
        Assert.assertFalse(text.contains("Collections.sort(this.b, new RecipeSorter(this));"));
        Assert.assertFalse(text.contains("this.b.add(new ShapedRecipes"));
    }
}
