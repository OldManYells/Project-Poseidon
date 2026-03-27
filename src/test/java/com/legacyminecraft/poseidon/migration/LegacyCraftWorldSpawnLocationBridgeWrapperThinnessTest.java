package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldSpawnLocationBridgeWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_SPAWN_LOCATION_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldSpawnLocationBridgeBehaviour.java");

    @Test
    public void craftWorldDelegatesSpawnLocationOverloadForwardingToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_SPAWN_LOCATION_BRIDGE_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public boolean setSpawnLocation(int x, int y, int z) {", "// Poseidon start");

        Assert.assertTrue(craftWorldText.contains("CraftWorldSpawnLocationBridgeBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_SPAWN_LOCATION_BRIDGE_BEHAVIOUR.setSpawnLocation(this, x, y, z)"));
        Assert.assertFalse(section.contains("return setSpawnLocation(x, y, z, 0f, 0f);"));

        Assert.assertTrue(behaviourText.contains("setSpawnLocation(CraftWorld craftWorld, int x, int y, int z)"));
        Assert.assertTrue(behaviourText.contains("return craftWorld.setSpawnLocation(x, y, z, 0f, 0f);"));
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
