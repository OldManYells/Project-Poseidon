package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldMetadataAccessWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_METADATA_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldMetadataAccessBehaviour.java");

    @Test
    public void craftWorldDelegatesMetadataAccessorWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_METADATA_ACCESS_BEHAVIOUR_PATH);
        String metadataSection = section(craftWorldText, "public Environment getEnvironment() {", "public Block getBlockAt(Location location) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldMetadataAccessBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_METADATA_ACCESS_BEHAVIOUR.getEnvironment(environment)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_METADATA_ACCESS_BEHAVIOUR.getGenerator(generator)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_METADATA_ACCESS_BEHAVIOUR.getPopulators(populators)"));

        Assert.assertFalse(metadataSection.contains("return environment;"));
        Assert.assertFalse(metadataSection.contains("return generator;"));
        Assert.assertFalse(metadataSection.contains("return populators;"));

        Assert.assertTrue(behaviourText.contains("getEnvironment(World.Environment environment)"));
        Assert.assertTrue(behaviourText.contains("return environment;"));
        Assert.assertTrue(behaviourText.contains("getGenerator(ChunkGenerator generator)"));
        Assert.assertTrue(behaviourText.contains("return generator;"));
        Assert.assertTrue(behaviourText.contains("getPopulators(List<BlockPopulator> populators)"));
        Assert.assertTrue(behaviourText.contains("return populators;"));
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
