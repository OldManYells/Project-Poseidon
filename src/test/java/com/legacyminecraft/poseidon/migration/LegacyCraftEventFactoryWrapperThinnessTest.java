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

        Assert.assertTrue(text.contains("EventFactoryInteractionSystem"));
        Assert.assertTrue(text.contains("EventFactoryLifecycleSystem"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_INTERACTION_SYSTEM"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_LIFECYCLE_SYSTEM"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_INTERACTION_SYSTEM.callBlockPlaceEvent("));
        Assert.assertTrue(text.contains("EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerBucketEmptyEvent("));
        Assert.assertTrue(text.contains("EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerBucketFillEvent("));
        Assert.assertTrue(text.contains("EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerInteractEvent(who, action, itemstack)"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerInteractEvent("));
        Assert.assertTrue(text.contains("EVENT_FACTORY_INTERACTION_SYSTEM.callBlockDamageEvent(who, x, y, z, itemstack, instaBreak)"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_LIFECYCLE_SYSTEM.callCreatureSpawnEvent(entityliving, spawnReason)"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_LIFECYCLE_SYSTEM.callEntityTameEvent(entity, tamer)"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_LIFECYCLE_SYSTEM.callItemSpawnEvent(entityitem)"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_LIFECYCLE_SYSTEM.callBlockFadeEvent(block, type)"));
        Assert.assertTrue(text.contains("EVENT_FACTORY_LIFECYCLE_SYSTEM.callItemDespawnEvent(entityitem)"));
        Assert.assertFalse(text.contains("CreatureSpawnTypeResolveBehaviour"));
        Assert.assertFalse(text.contains("BlockPlaceEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("SpawnBuildPermissionBehaviour"));
        Assert.assertFalse(text.contains("BucketEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("BlockDamageEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("ItemLifecycleEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("EntityTameEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("BlockFadeEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("CreatureSpawnEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("PlayerInteractEventConstructionBehaviour"));
        Assert.assertFalse(text.contains("PlayerInteractPreparationBehaviour"));
        Assert.assertFalse(text.contains("PlayerInteractActionValidationPolicy"));
        Assert.assertFalse(text.contains("EventDispatchBridgeBehaviour"));
        Assert.assertFalse(text.contains("EventFactoryContextBridgeBehaviour"));
        Assert.assertFalse(text.contains("EventFactoryBlockSelectionBehaviour"));
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
        Assert.assertFalse(text.contains("craftServer.getPluginManager().callEvent(event);"));
        Assert.assertFalse(text.contains("Bukkit.getPluginManager().callEvent(event);"));
        Assert.assertFalse(text.contains("((CraftServer) entity.getServer()).getPluginManager().callEvent(event);"));
        Assert.assertFalse(text.contains("Player player = (who == null) ? null : (Player) who.getBukkitEntity();"));
        Assert.assertFalse(text.contains("org.bukkit.entity.AnimalTamer bukkitTamer = (tamer != null ? (AnimalTamer) tamer.getBukkitEntity() : null);"));
        Assert.assertFalse(text.contains("Material bucket = Material.getMaterial(itemstack.id);"));
        Assert.assertFalse(text.contains("CraftItemStack itemInHand = new CraftItemStack(itemstack);"));
        Assert.assertFalse(text.contains("CraftWorld craftWorld = (CraftWorld) player.getWorld();"));
        Assert.assertFalse(text.contains("CraftServer craftServer = (CraftServer) player.getServer();"));
        Assert.assertFalse(text.contains("CraftWorld craftWorld = ((WorldServer) world).getWorld();"));
        Assert.assertFalse(text.contains("CraftServer craftServer = ((WorldServer) world).getServer();"));
        Assert.assertFalse(text.contains("CraftServer craftServer = (CraftServer) entity.getServer();"));
        Assert.assertFalse(text.contains("CraftServer craftServer = (CraftServer) bukkitEntity.getServer();"));
        Assert.assertFalse(text.contains("CraftItemStack itemInHand = new CraftItemStack(new ItemStack(item));"));
        Assert.assertFalse(text.contains("craftWorld.getBlockAt(clickedX, clickedY, clickedZ)"));
        Assert.assertFalse(text.contains("craftWorld.getBlockAt(x, y, z)"));
        Assert.assertFalse(text.contains("BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(clickedFace)"));
        Assert.assertFalse(text.contains("private static boolean canBuild(CraftWorld world, Player player, int x, int z)"));
        Assert.assertFalse(text.contains("net.minecraft.server.Block.byId[type]"));
        Assert.assertFalse(text.contains("new ItemStack(block)"));
    }
}
