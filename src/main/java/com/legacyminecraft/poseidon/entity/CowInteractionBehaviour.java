package com.legacyminecraft.poseidon.entity;

import com.legacyminecraft.poseidon.compat.bukkit.BucketEventBridgeBehaviour;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityHuman;
import net.minecraft.server.Item;
import net.minecraft.server.ItemStack;
import org.bukkit.Location;
import org.bukkit.event.player.PlayerBucketFillEvent;

public final class CowInteractionBehaviour {
    private static final CowInteractionBehaviour INSTANCE = new CowInteractionBehaviour();
    private static final BucketEventBridgeBehaviour BUCKET_EVENT_BRIDGE = BucketEventBridgeBehaviour.getInstance();

    private CowInteractionBehaviour() {
    }

    public static CowInteractionBehaviour getInstance() {
        return INSTANCE;
    }

    public String getAmbientSound() {
        return "mob.cow";
    }

    public String getHurtSound() {
        return "mob.cowhurt";
    }

    public String getDeathSound() {
        return "mob.cowhurt";
    }

    public float getSoundVolume() {
        return 0.4F;
    }

    public int getDropItemId() {
        return Item.LEATHER.id;
    }

    public boolean tryFillBucket(EntityCow cow, EntityHuman player, ItemStack itemInHand) {
        if (itemInHand == null || itemInHand.id != Item.BUCKET.id) {
            return false;
        }

        Location location = cow.getBukkitEntity().getLocation();
        PlayerBucketFillEvent event = BUCKET_EVENT_BRIDGE.callPlayerBucketFillEvent(
                player,
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                -1,
                itemInHand,
                Item.MILK_BUCKET
        );

        if (event.isCancelled()) {
            return false;
        }

        ItemStack resultStack = BUCKET_EVENT_BRIDGE.toNmsItemStack(event.getItemStack());
        player.inventory.setItem(player.inventory.itemInHandIndex, resultStack);
        return true;
    }
}
