package com.legacyminecraft.compat.bukkit;

public class PlayerBucketEmptyEvent extends PlayerBucketEvent {
    public PlayerBucketEmptyEvent(Player who, Block blockClicked, BlockFace blockFace, Material bucket, ItemStack itemInHand) {
        super(Type.PLAYER_BUCKET_EMPTY, who, blockClicked, blockFace, bucket, itemInHand);
    }
}
