package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldLocationWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path LOCATION_PROJECTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldLocationProjectionBehaviour.java");

    @Test
    public void craftWorldDelegatesLocationOverloadsToCanonicalProjectionBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(LOCATION_PROJECTION_BEHAVIOUR_PATH);

        String locationOverloadSection = section(craftWorldText,
                "public Block getBlockAt(Location location) {",
                "public ChunkGenerator getGenerator() {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldLocationProjectionBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_LOCATION_PROJECTION_BEHAVIOUR"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_LOCATION_PROJECTION_BEHAVIOUR.getBlockAt(this, location)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_LOCATION_PROJECTION_BEHAVIOUR.getBlockTypeIdAt(this, location)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_LOCATION_PROJECTION_BEHAVIOUR.getHighestBlockYAt(this, location)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_LOCATION_PROJECTION_BEHAVIOUR.getChunkAt(this, location)"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_LOCATION_PROJECTION_BEHAVIOUR.getHighestBlockAt(this, location)"));

        Assert.assertFalse(locationOverloadSection.contains("location.getBlockX()"));
        Assert.assertFalse(locationOverloadSection.contains("location.getBlockY()"));
        Assert.assertFalse(locationOverloadSection.contains("location.getBlockZ()"));
        Assert.assertFalse(locationOverloadSection.contains("getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ())"));
        Assert.assertFalse(locationOverloadSection.contains("getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ())"));
        Assert.assertFalse(locationOverloadSection.contains("getHighestBlockYAt(location.getBlockX(), location.getBlockZ())"));
        Assert.assertFalse(locationOverloadSection.contains("getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4)"));
        Assert.assertFalse(locationOverloadSection.contains("getHighestBlockAt(location.getBlockX(), location.getBlockZ())"));

        Assert.assertTrue(behaviourText.contains("public Block getBlockAt(CraftWorld craftWorld, Location location) {"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.getBlockAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.getBlockTypeIdAt(location.getBlockX(), location.getBlockY(), location.getBlockZ());"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.getHighestBlockYAt(location.getBlockX(), location.getBlockZ());"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.getChunkAt(location.getBlockX() >> 4, location.getBlockZ() >> 4);"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.getHighestBlockAt(location.getBlockX(), location.getBlockZ());"));
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
