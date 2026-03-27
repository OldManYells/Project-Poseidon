package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftInventory item conversion/copy bridge policy.
 */
public final class InventoryItemBridgeBehaviour {
    private static final InventoryItemBridgeBehaviour INSTANCE = new InventoryItemBridgeBehaviour();
    private static final ItemStackProjectionBridgeBehaviour ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR =
            ItemStackProjectionBridgeBehaviour.getInstance();

    private InventoryItemBridgeBehaviour() {
    }

    public static InventoryItemBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T wrapSingle(Object item) {
        return ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toCraftItemStack(item);
    }

    public Object[] toBukkitContents(Object[] source, int inventorySize) {
        Object[] items = new Object[inventorySize];
        for (int index = 0; index < source.length; index++) {
            Object item = source[index];
            items[index] = item == null ? null : ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toCraftItemStack(item);
        }
        return items;
    }

    public void copyContentsToNms(Object[] source, Object[] target) {
        for (int index = 0; index < source.length; index++) {
            target[index] = toNmsForContentsSlot(source[index]);
        }
    }

    public Object toNmsForSetItem(Object item) {
        return ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toNmsItemStack(BridgeReflection.cast(item));
    }

    private Object toNmsForContentsSlot(Object item) {
        if (item == null || ((Number) BridgeReflection.invoke(item, "getTypeId")).intValue() <= 0) {
            return null;
        }
        return ITEM_STACK_PROJECTION_BRIDGE_BEHAVIOUR.toNmsItemStack(BridgeReflection.cast(item));
    }
}
