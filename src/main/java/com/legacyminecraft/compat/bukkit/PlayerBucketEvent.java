package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat bucket-event base scaffold.
 */
public class PlayerBucketEvent extends PlayerEvent {
    private final Type bucketType;
    private final Block blockClicked;
    private final BlockFace blockFace;
    private final Material bucket;
    private final ItemStack itemInHand;
    private boolean cancelled;

    protected PlayerBucketEvent(
            Type bucketType,
            Player who,
            Block blockClicked,
            BlockFace blockFace,
            Material bucket,
            ItemStack itemInHand
    ) {
        super(who);
        this.bucketType = bucketType;
        this.blockClicked = blockClicked;
        this.blockFace = blockFace;
        this.bucket = bucket;
        this.itemInHand = itemInHand;
    }

    public Type getBucketType() {
        return bucketType;
    }

    public Block getBlockClicked() {
        return blockClicked;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }

    public Material getBucket() {
        return bucket;
    }

    public ItemStack getItemInHand() {
        return itemInHand;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
