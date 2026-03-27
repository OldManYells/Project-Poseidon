package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldCreationOrchestrationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_CREATION_ORCHESTRATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldCreationOrchestrationBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldTopLevelOrchestrationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_CREATION_ORCHESTRATION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldCreationOrchestrationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_CREATION_ORCHESTRATION_BEHAVIOUR.createWorld("));
        Assert.assertTrue(craftServerText.contains("new CraftServerWorldCreationOrchestrationBehaviour.WorldCreationActions()"));

        Assert.assertFalse(section.contains("File folder = new File(name);"));
        Assert.assertFalse(section.contains("CRAFT_SERVER_WORLD_CONVERSION_BEHAVIOUR.convertIfNeeded(folder, name, console, getLogger());"));
        Assert.assertFalse(section.contains("WorldServer internal = CRAFT_SERVER_WORLD_INSTANCE_CREATION_BEHAVIOUR.createWorldServer("));

        Assert.assertTrue(behaviourText.contains("createWorld(String name, Environment environment, long seed, ChunkGenerator generator, WorldCreationActions actions, World existingWorld)"));
        Assert.assertTrue(behaviourText.contains("File folder = new File(name);"));
        Assert.assertTrue(behaviourText.contains("World world = actions.resolveExistingWorldOrThrow(existingWorld, folder, name);"));
        Assert.assertTrue(behaviourText.contains("ChunkGenerator resolvedGenerator = actions.resolveGenerator(generator, name);"));
        Assert.assertTrue(behaviourText.contains("actions.convertIfNeeded(folder, name);"));
        Assert.assertTrue(behaviourText.contains("WorldServer internal = actions.createWorldServer(name, seed, environment, resolvedGenerator);"));
        Assert.assertTrue(behaviourText.contains("return actions.finalizeWorldCreation(internal, name, resolvedGenerator);"));
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
