package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftWorldSpawnLocationWrapperThinnessTest {
    private static final Path CRAFT_WORLD_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftWorld.java");
    private static final Path CRAFT_WORLD_SPAWN_LOCATION_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftWorldSpawnLocationAccessBehaviour.java");

    @Test
    public void craftWorldDelegatesSpawnLocationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftWorldText = read(CRAFT_WORLD_PATH);
        String behaviourText = read(CRAFT_WORLD_SPAWN_LOCATION_ACCESS_BEHAVIOUR_PATH);
        String section = section(craftWorldText, "public Location getSpawnLocation() {", "public boolean setSpawnLocation(int x, int y, int z) {");

        Assert.assertTrue(craftWorldText.contains("CraftWorldSpawnLocationAccessBehaviour"));
        Assert.assertTrue(craftWorldText.contains("CRAFT_WORLD_SPAWN_LOCATION_ACCESS_BEHAVIOUR.getSpawnLocation(this, world)"));
        Assert.assertFalse(section.contains("ChunkCoordinates spawn = world.getSpawn();"));
        Assert.assertFalse(section.contains("float yaw = world.worldData.getYaw();"));
        Assert.assertFalse(section.contains("float pitch = world.worldData.getPitch();"));
        Assert.assertFalse(section.contains("new Location(this, spawn.x, spawn.y, spawn.z, yaw, pitch)"));

        Assert.assertTrue(behaviourText.contains("getSpawnLocation(World world, WorldServer worldServer)"));
        Assert.assertTrue(behaviourText.contains("ChunkCoordinates spawn = worldServer.getSpawn();"));
        Assert.assertTrue(behaviourText.contains("float yaw = worldServer.worldData.getYaw();"));
        Assert.assertTrue(behaviourText.contains("float pitch = worldServer.worldData.getPitch();"));
        Assert.assertTrue(behaviourText.contains("return new Location(world, spawn.x, spawn.y, spawn.z, yaw, pitch);"));
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
