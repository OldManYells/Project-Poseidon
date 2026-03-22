package org.bukkit.craftbukkit.event;

import com.legacyminecraft.poseidon.compat.bukkit.CreatureSpawnTypeResolveBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.BlockPlaceEventConstructionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerInteractPreparationBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.SpawnBuildPermissionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.BucketEventConstructionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerInteractActionValidationPolicy;
import com.legacyminecraft.poseidon.compat.bukkit.BlockDamageEventConstructionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.ItemLifecycleEventConstructionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.EntityTameEventConstructionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.BlockFadeEventConstructionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.BlockFaceConversionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.PlayerInteractEventConstructionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CreatureSpawnEventConstructionBehaviour;
import net.minecraft.server.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.CraftServer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.AnimalTamer;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockFadeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityTameEvent;
import org.bukkit.event.entity.ItemDespawnEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class CraftEventFactory {
    private static final CreatureSpawnTypeResolveBehaviour CREATURE_SPAWN_TYPE_RESOLVE_BEHAVIOUR =
            CreatureSpawnTypeResolveBehaviour.getInstance();
    private static final BlockPlaceEventConstructionBehaviour BLOCK_PLACE_EVENT_CONSTRUCTION_BEHAVIOUR =
            BlockPlaceEventConstructionBehaviour.getInstance();
    private static final SpawnBuildPermissionBehaviour SPAWN_BUILD_PERMISSION_BEHAVIOUR =
            SpawnBuildPermissionBehaviour.getInstance();
    private static final BucketEventConstructionBehaviour BUCKET_EVENT_CONSTRUCTION_BEHAVIOUR =
            BucketEventConstructionBehaviour.getInstance();
    private static final PlayerInteractPreparationBehaviour PLAYER_INTERACT_PREPARATION_BEHAVIOUR =
            PlayerInteractPreparationBehaviour.getInstance();
    private static final PlayerInteractActionValidationPolicy PLAYER_INTERACT_ACTION_VALIDATION_POLICY =
            PlayerInteractActionValidationPolicy.getInstance();
    private static final BlockDamageEventConstructionBehaviour BLOCK_DAMAGE_EVENT_CONSTRUCTION_BEHAVIOUR =
            BlockDamageEventConstructionBehaviour.getInstance();
    private static final ItemLifecycleEventConstructionBehaviour ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR =
            ItemLifecycleEventConstructionBehaviour.getInstance();
    private static final EntityTameEventConstructionBehaviour ENTITY_TAME_EVENT_CONSTRUCTION_BEHAVIOUR =
            EntityTameEventConstructionBehaviour.getInstance();
    private static final BlockFadeEventConstructionBehaviour BLOCK_FADE_EVENT_CONSTRUCTION_BEHAVIOUR =
            BlockFadeEventConstructionBehaviour.getInstance();
    private static final BlockFaceConversionBehaviour BLOCK_FACE_CONVERSION_BEHAVIOUR =
            BlockFaceConversionBehaviour.getInstance();
    private static final PlayerInteractEventConstructionBehaviour PLAYER_INTERACT_EVENT_CONSTRUCTION_BEHAVIOUR =
            PlayerInteractEventConstructionBehaviour.getInstance();
    private static final CreatureSpawnEventConstructionBehaviour CREATURE_SPAWN_EVENT_CONSTRUCTION_BEHAVIOUR =
            CreatureSpawnEventConstructionBehaviour.getInstance();

    private static boolean canBuild(CraftWorld world, Player player, int x, int z) {
        return SPAWN_BUILD_PERMISSION_BEHAVIOUR.canBuild(world, player, x, z);
    }

    /**
     * Block place methods
     */
    public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, int type) {
        return callBlockPlaceEvent(world, who, replacedBlockState, clickedX, clickedY, clickedZ, net.minecraft.server.Block.byId[type]);
    }

    public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, net.minecraft.server.Block block) {
        return callBlockPlaceEvent(world, who, replacedBlockState, clickedX, clickedY, clickedZ, new ItemStack(block));
    }

    public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, ItemStack itemstack) {
        CraftWorld craftWorld = ((WorldServer) world).getWorld();
        CraftServer craftServer = ((WorldServer) world).getServer();

        Player player = (who == null) ? null : (Player) who.getBukkitEntity();
        CraftItemStack itemInHand = new CraftItemStack(itemstack);

        Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
        Block placedBlock = replacedBlockState.getBlock();

        boolean canBuild = canBuild(craftWorld, player, placedBlock.getX(), placedBlock.getZ());

        BlockPlaceEvent event = BLOCK_PLACE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockPlaceEvent(
                placedBlock, replacedBlockState, blockClicked, itemInHand, player, canBuild);
        craftServer.getPluginManager().callEvent(event);

        return event;
    }

    /**
     * Bucket methods
     */
    public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand) {
        return (PlayerBucketEmptyEvent) getPlayerBucketEvent(Type.PLAYER_BUCKET_EMPTY, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, Item.BUCKET);
    }

    public static PlayerBucketFillEvent callPlayerBucketFillEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand, net.minecraft.server.Item bucket) {
        return (PlayerBucketFillEvent) getPlayerBucketEvent(Type.PLAYER_BUCKET_FILL, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket);
    }

    private static PlayerEvent getPlayerBucketEvent(Type type, EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemstack, net.minecraft.server.Item item) {
        Player player = (who == null) ? null : (Player) who.getBukkitEntity();
        CraftItemStack itemInHand = new CraftItemStack(new ItemStack(item));
        Material bucket = Material.getMaterial(itemstack.id);

        CraftWorld craftWorld = (CraftWorld) player.getWorld();
        CraftServer craftServer = (CraftServer) player.getServer();

        Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
        BlockFace blockFace = BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(clickedFace);

        boolean canBuild = canBuild(craftWorld, player, clickedX, clickedZ);
        PlayerEvent event = BUCKET_EVENT_CONSTRUCTION_BEHAVIOUR.createBucketEvent(
                type, player, blockClicked, blockFace, bucket, itemInHand, canBuild);

        craftServer.getPluginManager().callEvent(event);

        return event;
    }

    /**
     * Player Interact event
     */

    public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, ItemStack itemstack) {
        PLAYER_INTERACT_ACTION_VALIDATION_POLICY.validateAirInteractionAction(action);
        return callPlayerInteractEvent(who, action, 0, 255, 0, 0, itemstack);
    }
    public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemstack) {
        Player player = (who == null) ? null : (Player) who.getBukkitEntity();
        CraftItemStack itemInHand = new CraftItemStack(itemstack);

        CraftWorld craftWorld = (CraftWorld) player.getWorld();
        CraftServer craftServer = (CraftServer) player.getServer();

        Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
        BlockFace blockFace = BLOCK_FACE_CONVERSION_BEHAVIOUR.notchToBlockFace(clickedFace);

        PlayerInteractPreparationBehaviour.PreparedInteraction preparedInteraction =
                PLAYER_INTERACT_PREPARATION_BEHAVIOUR.prepare(action, clickedY, itemInHand);
        action = preparedInteraction.getAction();
        if (preparedInteraction.useNullClickedBlock()) {
            blockClicked = null;
        }
        itemInHand = preparedInteraction.getItemInHand();

        PlayerInteractEvent event = PLAYER_INTERACT_EVENT_CONSTRUCTION_BEHAVIOUR.createPlayerInteractEvent(
                player, action, itemInHand, blockClicked, blockFace);
        craftServer.getPluginManager().callEvent(event);

        return event;
    }

    /**
     * BlockDamageEvent
     */
    public static BlockDamageEvent callBlockDamageEvent(EntityHuman who, int x, int y, int z, ItemStack itemstack, boolean instaBreak) {
        Player player = (who == null) ? null : (Player) who.getBukkitEntity();
        CraftItemStack itemInHand = new CraftItemStack(itemstack);

        CraftWorld craftWorld = (CraftWorld) player.getWorld();
        CraftServer craftServer = (CraftServer) player.getServer();

        Block blockClicked = craftWorld.getBlockAt(x, y, z);

        BlockDamageEvent event = BLOCK_DAMAGE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockDamageEvent(
                player, blockClicked, itemInHand, instaBreak);
        craftServer.getPluginManager().callEvent(event);

        return event;
    }

    /**
     * CreatureSpawnEvent
     */
    public static CreatureSpawnEvent callCreatureSpawnEvent(EntityLiving entityliving, SpawnReason spawnReason) {
        org.bukkit.entity.Entity entity = entityliving.getBukkitEntity();
        CraftServer craftServer = (CraftServer) entity.getServer();

        CreatureType type = CREATURE_SPAWN_TYPE_RESOLVE_BEHAVIOUR.resolve(entityliving);

        CreatureSpawnEvent event = CREATURE_SPAWN_EVENT_CONSTRUCTION_BEHAVIOUR.createCreatureSpawnEvent(
                entity, type, spawnReason);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }

    /**
     * EntityTameEvent
     */
    public static EntityTameEvent callEntityTameEvent(EntityLiving entity, EntityHuman tamer) {
        org.bukkit.entity.Entity bukkitEntity = entity.getBukkitEntity();
        org.bukkit.entity.AnimalTamer bukkitTamer = (tamer != null ? (AnimalTamer) tamer.getBukkitEntity() : null);
        CraftServer craftServer = (CraftServer) bukkitEntity.getServer();

        EntityTameEvent event = ENTITY_TAME_EVENT_CONSTRUCTION_BEHAVIOUR.createEntityTameEvent(
                bukkitEntity, bukkitTamer);
        craftServer.getPluginManager().callEvent(event);
        return event;
    }

    /**
     * ItemSpawnEvent
     */
    public static ItemSpawnEvent callItemSpawnEvent(EntityItem entityitem) {
        org.bukkit.entity.Entity entity = entityitem.getBukkitEntity();
        CraftServer craftServer = (CraftServer) entity.getServer();

        ItemSpawnEvent event = ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR.createItemSpawnEvent(entity);

        craftServer.getPluginManager().callEvent(event);
        return event;
    }

    /**
     * BlockFadeEvent
     */
    public static BlockFadeEvent callBlockFadeEvent(Block block, int type) {
        BlockFadeEvent event = BLOCK_FADE_EVENT_CONSTRUCTION_BEHAVIOUR.createBlockFadeEvent(block, type);
        Bukkit.getPluginManager().callEvent(event);
        return event;
    }

    /**
     * ItemDespawnEvent
     */
    public static ItemDespawnEvent callItemDespawnEvent(EntityItem entityitem) {
        org.bukkit.entity.Entity entity = entityitem.getBukkitEntity();

        ItemDespawnEvent event = ITEM_LIFECYCLE_EVENT_CONSTRUCTION_BEHAVIOUR.createItemDespawnEvent(entity);

        ((CraftServer) entity.getServer()).getPluginManager().callEvent(event);
        return event;
    }
}
