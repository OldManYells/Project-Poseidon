package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalEventBridgeDependencyDirectionTest {
    private static final Path PLAYER_INTERACT_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/PlayerInteractEventBridgeBehaviour.java");
    private static final Path BLOCK_PLACE_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/BlockPlaceEventBridgeBehaviour.java");
    private static final Path BUCKET_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/BucketEventBridgeBehaviour.java");
    private static final Path ENTITY_TAME_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/EntityTameEventBridgeBehaviour.java");
    private static final Path WORLD_ENTITY_SPAWN_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/WorldEntitySpawnEventBridgeBehaviour.java");
    private static final Path CRAFT_EVENT_FACTORY_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/event/CraftEventFactory.java");

    @Test
    public void canonicalEventBridgesDependOnCanonicalSystemsNotLegacyEventFactory() throws IOException {
        String playerInteractBridgeText = read(PLAYER_INTERACT_EVENT_BRIDGE_BEHAVIOUR_PATH);
        String blockPlaceBridgeText = read(BLOCK_PLACE_EVENT_BRIDGE_BEHAVIOUR_PATH);
        String bucketBridgeText = read(BUCKET_EVENT_BRIDGE_BEHAVIOUR_PATH);
        String entityTameBridgeText = read(ENTITY_TAME_EVENT_BRIDGE_BEHAVIOUR_PATH);
        String worldSpawnBridgeText = read(WORLD_ENTITY_SPAWN_EVENT_BRIDGE_BEHAVIOUR_PATH);
        String craftEventFactoryText = read(CRAFT_EVENT_FACTORY_PATH);

        assertNoLegacyEventFactoryDependency(playerInteractBridgeText);
        assertNoLegacyEventFactoryDependency(blockPlaceBridgeText);
        assertNoLegacyEventFactoryDependency(bucketBridgeText);
        assertNoLegacyEventFactoryDependency(entityTameBridgeText);
        assertNoLegacyEventFactoryDependency(worldSpawnBridgeText);

        Assert.assertTrue(playerInteractBridgeText.contains("EventFactoryInteractionSystem"));
        Assert.assertTrue(blockPlaceBridgeText.contains("EventFactoryInteractionSystem"));
        Assert.assertTrue(bucketBridgeText.contains("EventFactoryInteractionSystem"));
        Assert.assertTrue(entityTameBridgeText.contains("EventFactoryLifecycleSystem"));
        Assert.assertTrue(worldSpawnBridgeText.contains("EventFactoryLifecycleSystem"));

        Assert.assertTrue(craftEventFactoryText.contains("EventFactoryInteractionSystem"));
        Assert.assertTrue(craftEventFactoryText.contains("EventFactoryLifecycleSystem"));
        Assert.assertFalse(craftEventFactoryText.contains("CraftServer craftServer"));
        Assert.assertFalse(craftEventFactoryText.contains("CraftWorld craftWorld"));
        Assert.assertFalse(craftEventFactoryText.contains("CraftItemStack itemInHand"));
        Assert.assertFalse(craftEventFactoryText.contains("spawnSize"));
        Assert.assertFalse(craftEventFactoryText.contains("getPluginManager().callEvent"));
    }

    private static void assertNoLegacyEventFactoryDependency(String text) {
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.event.CraftEventFactory"));
        Assert.assertFalse(text.contains("CraftEventFactory."));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
