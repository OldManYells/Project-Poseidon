package org.bukkit.craftbukkit.event;

import com.legacyminecraft.compat.bukkit.EventFactoryInteractionSystem;
import com.legacyminecraft.compat.bukkit.EventFactoryLifecycleSystem;
import net.minecraft.server.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
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
    private static final EventFactoryInteractionSystem EVENT_FACTORY_INTERACTION_SYSTEM =
            EventFactoryInteractionSystem.getInstance();
    private static final EventFactoryLifecycleSystem EVENT_FACTORY_LIFECYCLE_SYSTEM =
            EventFactoryLifecycleSystem.getInstance();

    /**
     * Block place methods
     */
    public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, int type) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callBlockPlaceEvent(
                world, who, replacedBlockState, clickedX, clickedY, clickedZ, type
        );
    }

    public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, net.minecraft.server.Block block) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callBlockPlaceEvent(
                world, who, replacedBlockState, clickedX, clickedY, clickedZ, block
        );
    }

    public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ, ItemStack itemstack) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callBlockPlaceEvent(
                world, who, replacedBlockState, clickedX, clickedY, clickedZ, itemstack);
    }

    /**
     * Bucket methods
     */
    public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerBucketEmptyEvent(
                who, clickedX, clickedY, clickedZ, clickedFace, itemInHand
        );
    }

    public static PlayerBucketFillEvent callPlayerBucketFillEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand, net.minecraft.server.Item bucket) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerBucketFillEvent(
                who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket
        );
    }

    /**
     * Player Interact event
     */

    public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, ItemStack itemstack) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerInteractEvent(who, action, itemstack);
    }
    public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemstack) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerInteractEvent(
                who, action, clickedX, clickedY, clickedZ, clickedFace, itemstack
        );
    }

    /**
     * BlockDamageEvent
     */
    public static BlockDamageEvent callBlockDamageEvent(EntityHuman who, int x, int y, int z, ItemStack itemstack, boolean instaBreak) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callBlockDamageEvent(who, x, y, z, itemstack, instaBreak);
    }

    /**
     * CreatureSpawnEvent
     */
    public static CreatureSpawnEvent callCreatureSpawnEvent(EntityLiving entityliving, SpawnReason spawnReason) {
        return EVENT_FACTORY_LIFECYCLE_SYSTEM.callCreatureSpawnEvent(entityliving, spawnReason);
    }

    /**
     * EntityTameEvent
     */
    public static EntityTameEvent callEntityTameEvent(EntityLiving entity, EntityHuman tamer) {
        return EVENT_FACTORY_LIFECYCLE_SYSTEM.callEntityTameEvent(entity, tamer);
    }

    /**
     * ItemSpawnEvent
     */
    public static ItemSpawnEvent callItemSpawnEvent(EntityItem entityitem) {
        return EVENT_FACTORY_LIFECYCLE_SYSTEM.callItemSpawnEvent(entityitem);
    }

    /**
     * BlockFadeEvent
     */
    public static BlockFadeEvent callBlockFadeEvent(Block block, int type) {
        return EVENT_FACTORY_LIFECYCLE_SYSTEM.callBlockFadeEvent(block, type);
    }

    /**
     * ItemDespawnEvent
     */
    public static ItemDespawnEvent callItemDespawnEvent(EntityItem entityitem) {
        return EVENT_FACTORY_LIFECYCLE_SYSTEM.callItemDespawnEvent(entityitem);
    }
}
