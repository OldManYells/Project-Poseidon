package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftEventFactoryWrapperThinnessTest {
    private static final Path CRAFT_EVENT_FACTORY_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/event/CraftEventFactory.java");

    @Test
    public void craftEventFactoryDelegatesCreatureSpawnTypeResolutionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_EVENT_FACTORY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CreatureSpawnTypeResolveBehaviour"));
        Assert.assertTrue(text.contains("BlockPlaceEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("SpawnBuildPermissionBehaviour"));
        Assert.assertTrue(text.contains("BucketEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("BlockDamageEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("ItemLifecycleEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("EntityTameEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("BlockFadeEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("BlockFaceConversionBehaviour"));
        Assert.assertTrue(text.contains("CreatureSpawnEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("PlayerInteractEventConstructionBehaviour"));
        Assert.assertTrue(text.contains("PlayerInteractPreparationBehaviour"));
        Assert.assertTrue(text.contains("PlayerInteractActionValidationPolicy"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_TYPE_RESOLVE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("BLOCK_PLACE_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("SPAWN_BUILD_PERMISSION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("BUCKET_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("BLOCK_DAMAGE_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("ENTITY_TAME_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("BLOCK_FADE_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("PLAYER_INTERACT_EVENT_CONSTRUCTION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("PLAYER_INTERACT_PREPARATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("PLAYER_INTERACT_ACTION_VALIDATION_POLICY"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_TYPE_RESOLVE_BEHAVIOUR.resolve(entityliving)"));
        Assert.assertTrue(text.contains("BLOCK_PLACE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockPlaceEvent("));
        Assert.assertTrue(text.contains("SPAWN_BUILD_PERMISSION_BEHAVIOUR.canBuild(world, player, x, z)"));
        Assert.assertTrue(text.contains("BUCKET_EVENT_CONSTRUCTION_BEHAVIOUR.createBucketEvent("));
        Assert.assertTrue(text.contains("BLOCK_DAMAGE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockDamageEvent("));
        Assert.assertTrue(text.contains("ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR.createItemSpawnEvent(entity)"));
        Assert.assertTrue(text.contains("ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR.createItemDespawnEvent(entity)"));
        Assert.assertTrue(text.contains("ENTITY_TAME_EVENT_CONSTRUCTION_BEHAVIOUR.createEntityTameEvent("));
        Assert.assertTrue(text.contains("BLOCK_FADE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockFadeEvent(block, type)"));
        Assert.assertTrue(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(clickedFace)"));
        Assert.assertTrue(text.contains("CREATURE_SPAWN_EVENT_CONSTRUCTION_BEHAVIOUR.createCreatureSpawnEvent("));
        Assert.assertTrue(text.contains("PLAYER_INTERACT_EVENT_CONSTRUCTION_BEHAVIOUR.createPlayerInteractEvent("));
        Assert.assertTrue(text.contains("PLAYER_INTERACT_PREPARATION_BEHAVIOUR.prepare(action, clickedY, itemInHand)"));
        Assert.assertTrue(text.contains("PLAYER_INTERACT_ACTION_VALIDATION_POLICY.validateAirInteractionAction(action)"));
        Assert.assertFalse(text.contains("if (entityliving instanceof EntityChicken)"));
        Assert.assertFalse(text.contains("new BlockPlaceEvent("));
        Assert.assertFalse(text.contains("else if (entityliving instanceof EntityZombie)"));
        Assert.assertFalse(text.contains("type = CreatureType.MONSTER;"));
        Assert.assertFalse(text.contains("int spawnSize = Bukkit.getServer().getSpawnRadius();"));
        Assert.assertFalse(text.contains("ChunkCoordinates chunkcoordinates = worldServer.getSpawn();"));
        Assert.assertFalse(text.contains("if (clickedY == 255)"));
        Assert.assertFalse(text.contains("itemInHand.getType() == Material.AIR"));
        Assert.assertFalse(text.contains("type == Type.PLAYER_BUCKET_EMPTY"));
        Assert.assertFalse(text.contains("type == Type.PLAYER_BUCKET_FILL"));
        Assert.assertFalse(text.contains("if (action != Action.LEFT_CLICK_AIR && action != Action.RIGHT_CLICK_AIR)"));
        Assert.assertFalse(text.contains("new BlockDamageEvent("));
        Assert.assertFalse(text.contains("new ItemSpawnEvent(entity, entity.getLocation())"));
        Assert.assertFalse(text.contains("new ItemDespawnEvent(entity, entity.getLocation())"));
        Assert.assertFalse(text.contains("new EntityTameEvent(bukkitEntity, bukkitTamer)"));
        Assert.assertFalse(text.contains("state.setTypeId(type);"));
        Assert.assertFalse(text.contains("new BlockFadeEvent(block, state)"));
        Assert.assertFalse(text.contains("CraftBlock.notchToBlockFace(clickedFace)"));
        Assert.assertFalse(text.contains("new CreatureSpawnEvent(entity, type, entity.getLocation(), spawnReason)"));
        Assert.assertFalse(text.contains("new PlayerInteractEvent(player, action, itemInHand, blockClicked, blockFace)"));
    }
}
