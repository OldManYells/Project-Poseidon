package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import org.bukkit.craftbukkit.event.CraftEventFactory;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

/**
 * Canonical Bukkit bridge behaviour for bucket fill/empty event hooks.
 */
public final class BucketEventBridgeBehaviour {
    private static final BucketEventBridgeBehaviour INSTANCE = new BucketEventBridgeBehaviour();

    private BucketEventBridgeBehaviour() {
    }

    public static BucketEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public PlayerBucketFillEvent callPlayerBucketFillEvent(
            EntityHuman player,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            ItemStack itemInHand,
            Item bucketResult
    ) {
        return CraftEventFactory.callPlayerBucketFillEvent(
                player,
                clickedX,
                clickedY,
                clickedZ,
                clickedFace,
                itemInHand,
                bucketResult
        );
    }

    public PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(
            EntityHuman player,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            ItemStack itemInHand
    ) {
        return CraftEventFactory.callPlayerBucketEmptyEvent(
                player,
                clickedX,
                clickedY,
                clickedZ,
                clickedFace,
                itemInHand
        );
    }

    public ItemStack toNmsItemStack(org.bukkit.inventory.ItemStack bukkitStack) {
        if (bukkitStack == null) {
            return null;
        }

        byte data = bukkitStack.getData() == null ? (byte) 0 : bukkitStack.getData().getData();
        return new ItemStack(bukkitStack.getTypeId(), bukkitStack.getAmount(), data);
    }
}
