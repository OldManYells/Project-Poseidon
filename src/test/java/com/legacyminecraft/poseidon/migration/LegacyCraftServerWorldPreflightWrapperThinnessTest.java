package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldPreflightWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_PREFLIGHT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldPreflightBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldPreflightWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_PREFLIGHT_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldPreflightBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_PREFLIGHT_BEHAVIOUR.resolveExistingWorldOrThrow(existingWorld, folder, worldName)"));

        Assert.assertFalse(section.contains("World world = getWorld(name);"));
        Assert.assertFalse(section.contains("if ((folder.exists()) && (!folder.isDirectory())) {"));
        Assert.assertFalse(section.contains("File exists with the name '\" + name + \"' and isn't a folder"));

        Assert.assertTrue(behaviourText.contains("resolveExistingWorldOrThrow(World existingWorld, File folder, String name)"));
        Assert.assertTrue(behaviourText.contains("if (existingWorld != null) {"));
        Assert.assertTrue(behaviourText.contains("if ((folder.exists()) && (!folder.isDirectory())) {"));
        Assert.assertTrue(behaviourText.contains("throw new IllegalArgumentException(\"File exists with the name '\" + name + \"' and isn't a folder\");"));
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
