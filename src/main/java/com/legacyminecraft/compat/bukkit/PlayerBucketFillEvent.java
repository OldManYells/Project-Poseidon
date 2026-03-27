package com.legacyminecraft.compat.bukkit;

public class PlayerBucketFillEvent extends PlayerBucketEvent {
    public PlayerBucketFillEvent(Player who, Block blockClicked, BlockFace blockFace, Material bucket, ItemStack itemInHand) {
        super(Type.PLAYER_BUCKET_FILL, who, blockClicked, blockFace, bucket, itemInHand);
    }
}
