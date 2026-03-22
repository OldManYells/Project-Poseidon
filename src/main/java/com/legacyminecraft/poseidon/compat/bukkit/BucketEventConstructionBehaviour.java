package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Type;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;
import org.bukkit.event.player.PlayerEvent;

/**
 * Canonical behaviour for CraftEventFactory bucket-event construction and cancellation policy.
 */
public final class BucketEventConstructionBehaviour {
    private static final BucketEventConstructionBehaviour INSTANCE = new BucketEventConstructionBehaviour();

    private BucketEventConstructionBehaviour() {
    }

    public static BucketEventConstructionBehaviour getInstance() {
        return INSTANCE;
    }

    public PlayerEvent createBucketEvent(Type eventType, Player player, Block clickedBlock, BlockFace clickedFace,
                                         Material bucketMaterial, CraftItemStack itemInHand, boolean canBuild) {
        if (eventType == Type.PLAYER_BUCKET_EMPTY) {
            PlayerBucketEmptyEvent event = new PlayerBucketEmptyEvent(
                    player, clickedBlock, clickedFace, bucketMaterial, itemInHand);
            event.setCancelled(!canBuild);
            return event;
        }
        if (eventType == Type.PLAYER_BUCKET_FILL) {
            PlayerBucketFillEvent event = new PlayerBucketFillEvent(
                    player, clickedBlock, clickedFace, bucketMaterial, itemInHand);
            event.setCancelled(!canBuild);
            return event;
        }
        return null;
    }
}
