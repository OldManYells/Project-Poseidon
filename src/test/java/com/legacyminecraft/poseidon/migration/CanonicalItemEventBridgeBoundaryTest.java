package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalItemEventBridgeBoundaryTest {
    private static final Path BOAT_ITEM_PLACEMENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/BoatItemPlacementBehaviour.java");
    private static final Path MINECART_ITEM_PLACEMENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/MinecartItemPlacementBehaviour.java");
    private static final Path ITEM_BLOCK_PLACEMENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/ItemBlockPlacementBehaviour.java");
    private static final Path HOE_TILLING_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/HoeTillingBehaviour.java");
    private static final Path SEEDS_ITEM_PLACEMENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/SeedsItemPlacementBehaviour.java");
    private static final Path FLINT_AND_STEEL_ITEM_PLACEMENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/FlintAndSteelItemPlacementBehaviour.java");
    private static final Path BUCKET_ITEM_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/BucketItemBehaviour.java");
    private static final Path PAINTING_ITEM_PLACEMENT_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/PaintingItemPlacementBehaviour.java");
    private static final Path ITEM_STACK_INTERACTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/item/ItemStackInteractionBehaviour.java");
    private static final Path COW_INTERACTION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/CowInteractionBehaviour.java");
    private static final Path PLAYER_DEATH_HANDLING_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/world/player/PlayerDeathHandlingSystem.java");
    private static final Path PLAYER_INTERACT_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/PlayerInteractEventBridgeBehaviour.java");
    private static final Path BLOCK_PLACE_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/BlockPlaceEventBridgeBehaviour.java");
    private static final Path BUCKET_EVENT_BRIDGE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/BucketEventBridgeBehaviour.java");

    @Test
    public void canonicalItemAndCowBehavioursUseCompatEventBridges() throws IOException {
        assertNoDirectCraftBukkitEventImports(read(BOAT_ITEM_PLACEMENT_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(MINECART_ITEM_PLACEMENT_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(ITEM_BLOCK_PLACEMENT_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(HOE_TILLING_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(SEEDS_ITEM_PLACEMENT_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(FLINT_AND_STEEL_ITEM_PLACEMENT_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(BUCKET_ITEM_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(PAINTING_ITEM_PLACEMENT_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(ITEM_STACK_INTERACTION_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(COW_INTERACTION_BEHAVIOUR_PATH));
        assertNoDirectCraftBukkitEventImports(read(PLAYER_DEATH_HANDLING_SYSTEM_PATH));

        String playerInteractBridgeText = read(PLAYER_INTERACT_EVENT_BRIDGE_BEHAVIOUR_PATH);
        String blockPlaceBridgeText = read(BLOCK_PLACE_EVENT_BRIDGE_BEHAVIOUR_PATH);
        String bucketBridgeText = read(BUCKET_EVENT_BRIDGE_BEHAVIOUR_PATH);

        Assert.assertTrue(playerInteractBridgeText.contains("CraftEventFactory.callPlayerInteractEvent"));
        Assert.assertTrue(blockPlaceBridgeText.contains("CraftEventFactory.callBlockPlaceEvent"));
        Assert.assertTrue(bucketBridgeText.contains("CraftEventFactory.callPlayerBucketFillEvent"));
        Assert.assertTrue(bucketBridgeText.contains("CraftEventFactory.callPlayerBucketEmptyEvent"));
    }

    private static void assertNoDirectCraftBukkitEventImports(String text) {
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.event.CraftEventFactory"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.inventory.CraftItemStack"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.block.CraftBlockState"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.block.CraftBlock"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
