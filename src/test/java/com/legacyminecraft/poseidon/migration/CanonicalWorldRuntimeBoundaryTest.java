package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalWorldRuntimeBoundaryTest {
    private static final Path CONSOLE_COMMAND_FEEDBACK_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ConsoleCommandFeedbackService.java");
    private static final Path LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/LightningStormLifecycleBehaviour.java");
    private static final Path WORLD_SPAWN_PLACEMENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/WorldSpawnPlacementBehaviour.java");
    private static final Path WORLD_MAP_PERSISTENCE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/map/WorldMapPersistenceBehaviour.java");

    @Test
    public void canonicalWorldRuntimeCodeAvoidsDirectCraftPlayerAndCraftWorldImports() throws IOException {
        String consoleFeedbackText = read(CONSOLE_COMMAND_FEEDBACK_SERVICE_PATH);
        String lightningLifecycleText = read(LIGHTNING_STORM_LIFECYCLE_BEHAVIOUR_PATH);
        String worldSpawnPlacementText = read(WORLD_SPAWN_PLACEMENT_BEHAVIOUR_PATH);
        String worldMapPersistenceText = read(WORLD_MAP_PERSISTENCE_BEHAVIOUR_PATH);

        Assert.assertFalse(consoleFeedbackText.contains("org.bukkit.craftbukkit.entity.CraftPlayer"));
        Assert.assertTrue(consoleFeedbackText.contains("String senderName = null;"));

        Assert.assertFalse(lightningLifecycleText.contains("org.bukkit.craftbukkit.CraftWorld"));
        Assert.assertTrue(lightningLifecycleText.contains("org.bukkit.World bukkitWorld"));

        Assert.assertFalse(worldSpawnPlacementText.contains("org.bukkit.craftbukkit.CraftWorld"));
        Assert.assertTrue(worldSpawnPlacementText.contains("canSpawn(org.bukkit.World world, WorldProvider worldProvider, ChunkGenerator generator, int x, int z)"));

        Assert.assertFalse(worldMapPersistenceText.contains("org.bukkit.craftbukkit.CraftWorld"));
        Assert.assertTrue(worldMapPersistenceText.contains("WorldDimensionBridgeBehaviour"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
