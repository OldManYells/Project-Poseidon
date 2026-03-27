package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for bucket fill/empty event hooks.
 */
public final class BucketEventBridgeBehaviour {
    private static final BucketEventBridgeBehaviour INSTANCE = new BucketEventBridgeBehaviour();
    private static final EventFactoryInteractionSystem EVENT_FACTORY_INTERACTION_SYSTEM =
            EventFactoryInteractionSystem.getInstance();

    private BucketEventBridgeBehaviour() {
    }

    public static BucketEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public PlayerBucketFillEvent callPlayerBucketFillEvent(
            com.legacyminecraft.poseidon.item.EntityHuman player,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            com.legacyminecraft.poseidon.item.ItemStack itemInHand,
            com.legacyminecraft.poseidon.item.Item bucketResult
    ) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerBucketFillEvent(
                player,
                clickedX,
                clickedY,
                clickedZ,
                clickedFace,
                new ItemStack(itemInHand.id, itemInHand.count, itemInHand.damage),
                resolveBucketItem(bucketResult)
        );
    }

    public PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(
            com.legacyminecraft.poseidon.item.EntityHuman player,
            int clickedX,
            int clickedY,
            int clickedZ,
            int clickedFace,
            com.legacyminecraft.poseidon.item.ItemStack itemInHand
    ) {
        return EVENT_FACTORY_INTERACTION_SYSTEM.callPlayerBucketEmptyEvent(
                player,
                clickedX,
                clickedY,
                clickedZ,
                clickedFace,
                new ItemStack(itemInHand.id, itemInHand.count, itemInHand.damage)
        );
    }

    public net.minecraft.server.ItemStack toNmsItemStack(org.bukkit.inventory.ItemStack bukkitStack) {
        if (bukkitStack == null) {
            return null;
        }

        return new net.minecraft.server.ItemStack(
                bukkitStack.getTypeId(),
                bukkitStack.getAmount(),
                bukkitStack.getDurability()
        );
    }

    private Item resolveBucketItem(com.legacyminecraft.poseidon.item.Item bucketResult) {
        if (bucketResult == null) {
            return null;
        }

        Item compatBucket = Item.byId[bucketResult.id];
        if (compatBucket != null) {
            return compatBucket;
        }

        return new Item(bucketResult.id, bucketResult.a());
    }
}
