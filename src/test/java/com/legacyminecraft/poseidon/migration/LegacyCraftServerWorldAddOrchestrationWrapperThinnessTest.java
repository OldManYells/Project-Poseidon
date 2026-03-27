package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldAddOrchestrationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_ADD_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldAddOrchestrationBehaviour.java");

    @Test
    public void craftServerDelegatesAddWorldOrchestrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_ADD_ORCHESTRATION_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void addWorld(World world) {", "public Logger getLogger() {");

        Assert.assertTrue(craftServerText.contains("CraftServerWorldAddOrchestrationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_ADD_ORCHESTRATION_BEHAVIOUR.addWorld("));
        Assert.assertTrue(craftServerText.contains("new CraftServerWorldAddOrchestrationBehaviour.WorldRegistryAddAction()"));
        Assert.assertTrue(craftServerText.contains("new CraftServerWorldAddOrchestrationBehaviour.DuplicateWorldWarningAction()"));

        Assert.assertFalse(section.contains("if (!CRAFT_SERVER_WORLD_REGISTRY_BEHAVIOUR.addWorld(worlds, world)) {"));
        Assert.assertFalse(section.contains("return;"));

        Assert.assertTrue(behaviourText.contains("addWorld(World world, WorldRegistryAddAction addAction, DuplicateWorldWarningAction warningAction)"));
        Assert.assertTrue(behaviourText.contains("if (!addAction.addWorld(world)) {"));
        Assert.assertTrue(behaviourText.contains("warningAction.warnDuplicateWorld(world);"));
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
