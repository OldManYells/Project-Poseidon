package com.legacyminecraft.compat.bukkit;


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
