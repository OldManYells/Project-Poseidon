package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldInstanceCreationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_INSTANCE_CREATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldInstanceCreationBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldInstanceConstructionWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_INSTANCE_CREATION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldInstanceCreationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_INSTANCE_CREATION_BEHAVIOUR.createWorldServer("));

        Assert.assertFalse(section.contains("int dimension = 10 + console.worlds.size();"));
        Assert.assertFalse(section.contains("new WorldServer(console, new ServerNBTManager(new File(\".\"), name, true), name, dimension, seed, environment, generator)"));

        Assert.assertTrue(behaviourText.contains("createWorldServer(MinecraftServer console, String name, long seed, Environment environment, ChunkGenerator generator)"));
        Assert.assertTrue(behaviourText.contains("int dimension = 10 + console.worlds.size();"));
        Assert.assertTrue(behaviourText.contains("return new WorldServer(console, new ServerNBTManager(new File(\".\"), name, true), name, dimension, seed, environment, generator);"));
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
