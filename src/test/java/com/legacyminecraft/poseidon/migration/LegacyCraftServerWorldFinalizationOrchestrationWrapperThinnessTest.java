package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldFinalizationOrchestrationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_FINALIZATION_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldFinalizationOrchestrationBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldFinalizationOrchestrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_FINALIZATION_ORCHESTRATION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldFinalizationOrchestrationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_FINALIZATION_ORCHESTRATION_BEHAVIOUR.finalizeWorldCreation("));
        Assert.assertTrue(craftServerText.contains("new CraftServerWorldFinalizationOrchestrationBehaviour.FinalizationActions()"));

        Assert.assertFalse(section.contains("if (!CRAFT_SERVER_WORLD_REGISTRATION_GUARD_BEHAVIOUR.canCompleteCreateWorld(worlds, name)) {"));

        Assert.assertTrue(behaviourText.contains("finalizeWorldCreation(FinalizationActions actions)"));
        Assert.assertTrue(behaviourText.contains("if (!actions.canComplete()) {"));
        Assert.assertTrue(behaviourText.contains("actions.registerWorld();"));
        Assert.assertTrue(behaviourText.contains("actions.registerPopulators();"));
        Assert.assertTrue(behaviourText.contains("actions.dispatchWorldInit();"));
        Assert.assertTrue(behaviourText.contains("actions.announcePreparation();"));
        Assert.assertTrue(behaviourText.contains("actions.prepareSpawn();"));
        Assert.assertTrue(behaviourText.contains("actions.dispatchWorldLoad();"));
        Assert.assertTrue(behaviourText.contains("return actions.getWorld();"));
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
