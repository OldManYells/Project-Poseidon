package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldCreationBridgeWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_CREATION_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldCreationBridgeBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldOverloadBridgeWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_CREATION_BRIDGE_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public World createWorld(String name, World.Environment environment) {", "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {");

        Assert.assertTrue(craftServerText.contains("CraftServerWorldCreationBridgeBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_CREATION_BRIDGE_BEHAVIOUR.createWorld("));
        Assert.assertTrue(craftServerText.contains("new CraftServerWorldCreationBridgeBehaviour.WorldCreationDelegate()"));

        Assert.assertFalse(section.contains("(new Random()).nextLong()"));
        Assert.assertFalse(section.contains("return createWorld(name, environment, seed, null);"));
        Assert.assertFalse(section.contains("return createWorld(name, environment, (new Random()).nextLong(), generator);"));

        Assert.assertTrue(behaviourText.contains("createWorld(String name, Environment environment, WorldCreationDelegate delegate)"));
        Assert.assertTrue(behaviourText.contains("return delegate.createWorld(name, environment, this.createRandomSeed(), null);"));
        Assert.assertTrue(behaviourText.contains("createWorld(String name, Environment environment, long seed, WorldCreationDelegate delegate)"));
        Assert.assertTrue(behaviourText.contains("return delegate.createWorld(name, environment, seed, null);"));
        Assert.assertTrue(behaviourText.contains("createWorld(String name, Environment environment, ChunkGenerator generator, WorldCreationDelegate delegate)"));
        Assert.assertTrue(behaviourText.contains("return delegate.createWorld(name, environment, this.createRandomSeed(), generator);"));
        Assert.assertTrue(behaviourText.contains("return (new Random()).nextLong();"));
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
