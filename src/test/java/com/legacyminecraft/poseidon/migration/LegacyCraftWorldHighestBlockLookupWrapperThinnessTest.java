package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldHighestBlockLookupWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_HIGHEST_BLOCK_LOOKUP_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldHighestBlockLookupBehaviour.java");

    @Test
    public void craftWorldDelegatesHighestBlockLookupWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_HIGHEST_BLOCK_LOOKUP_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public Block getHighestBlockAt(int x, int z) {", "public Block getHighestBlockAt(Location location) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldHighestBlockLookupBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_HIGHEST_BLOCK_LOOKUP_BEHAVIOUR.getHighestBlockAt(this, x, z)"));
        Assert.assertFalse(section.contains("return getBlockAt(x, getHighestBlockYAt(x, z), z);"));

        Assert.assertTrue(behaviourText.contains("getHighestBlockAt(CraftWorld craftWorld, int x, int z)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.getBlockAt(x, craftWorld.getHighestBlockYAt(x, z), z);"));
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
