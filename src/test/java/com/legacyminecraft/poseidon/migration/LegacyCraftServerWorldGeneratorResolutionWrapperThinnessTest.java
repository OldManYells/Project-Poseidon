package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWorldGeneratorResolutionWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_WORLD_GENERATOR_RESOLUTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerWorldGeneratorResolutionBehaviour.java");

    @Test
    public void craftServerDelegatesCreateWorldGeneratorResolutionWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_WORLD_GENERATOR_RESOLUTION_BEHAVIOUR_PATH);
        String section = section(
                craftServerText,
                "public World createWorld(String name, Environment environment, long seed, ChunkGenerator generator) {",
                "public boolean unloadWorld(String name, boolean save) {"
        );

        Assert.assertTrue(craftServerText.contains("CraftServerWorldGeneratorResolutionBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_WORLD_GENERATOR_RESOLUTION_BEHAVIOUR.resolve("));
        Assert.assertTrue(craftServerText.contains("new CraftServerWorldGeneratorResolutionBehaviour.GeneratorLookup()"));

        Assert.assertFalse(section.contains("if (generator == null) {"));
        Assert.assertFalse(section.contains("generator = getGenerator(name);"));

        Assert.assertTrue(behaviourText.contains("resolve(ChunkGenerator generator, String worldName, GeneratorLookup lookup)"));
        Assert.assertTrue(behaviourText.contains("if (generator == null) {"));
        Assert.assertTrue(behaviourText.contains("return lookup.getGenerator(worldName);"));
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
