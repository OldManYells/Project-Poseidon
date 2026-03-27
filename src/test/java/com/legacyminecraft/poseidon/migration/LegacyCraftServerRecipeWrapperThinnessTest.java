package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerRecipeWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/RecipeRegistrationOrchestrationBehaviour.java");

    @Test
    public void craftServerRecipeRegistrationDelegatesBranchingAndBridgingToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String addRecipeSection = section(craftServerText,
                "public boolean addRecipe(Recipe recipe) {",
                "public Map<String, String[]> getCommandAliases() {");
        String behaviourText = read(RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR_PATH);

        Assert.assertTrue(craftServerText.contains("RecipeRegistrationOrchestrationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RECIPE_REGISTRATION_ORCHESTRATION_BEHAVIOUR.addRecipe(recipe)"));
        Assert.assertFalse(addRecipeSection.contains("CraftRecipe toAdd;"));
        Assert.assertFalse(addRecipeSection.contains("instanceof CraftRecipe"));
        Assert.assertFalse(addRecipeSection.contains("CraftShapedRecipe.fromBukkitRecipe"));
        Assert.assertFalse(addRecipeSection.contains("CraftShapelessRecipe.fromBukkitRecipe"));
        Assert.assertFalse(addRecipeSection.contains("CraftFurnaceRecipe.fromBukkitRecipe"));
        Assert.assertFalse(addRecipeSection.contains("toAdd.addToCraftingManager()"));

        Assert.assertTrue(behaviourText.contains("addRecipe(Recipe recipe)"));
        Assert.assertTrue(behaviourText.contains("RecipeAdapterBridgeBehaviour"));
        Assert.assertTrue(behaviourText.contains("RecipeWrapperProjectionBridgeBehaviour"));
        Assert.assertTrue(behaviourText.contains("resolveCraftRecipe(recipe)"));
        Assert.assertFalse(behaviourText.contains("if (recipe instanceof CraftRecipe)"));
        Assert.assertTrue(behaviourText.contains("fromBukkitShapedRecipe((ShapedRecipe) recipe)"));
        Assert.assertTrue(behaviourText.contains("fromBukkitShapelessRecipe((ShapelessRecipe) recipe)"));
        Assert.assertTrue(behaviourText.contains("fromBukkitFurnaceRecipe((FurnaceRecipe) recipe)"));
        Assert.assertTrue(behaviourText.contains("recipeToAdd.addToCraftingManager()"));
        Assert.assertTrue(behaviourText.contains("return false;"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
