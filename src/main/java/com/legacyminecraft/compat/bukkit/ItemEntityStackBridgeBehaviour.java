package com.legacyminecraft.compat.bukkit;


/**
 * Canonical bridge for CraftItem stack conversion between Bukkit and NMS item entities.
 */
public final class ItemEntityStackBridgeBehaviour {
    private static final ItemEntityStackBridgeBehaviour INSTANCE = new ItemEntityStackBridgeBehaviour();
    private static final ItemStackProjectionBridgeBehaviour ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR =
            ItemStackProjectionBridgeBehaviour.getInstance();

    private ItemEntityStackBridgeBehaviour() {
    }

    public static ItemEntityStackBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public ItemStack toBukkitItemStack(EntityItem itemEntity) {
        return ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toCraftItemStack(itemEntity.itemStack);
    }

    public void applyBukkitItemStack(EntityItem itemEntity, ItemStack stack) {
        itemEntity.itemStack = ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toNmsItemStack(stack);
    }
}
