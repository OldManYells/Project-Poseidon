package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit-compat bridge for CraftInventory transaction event dispatch/cancellation.
 */
public final class InventoryTransactionEventBridgeBehaviour {
    private static final InventoryTransactionEventBridgeBehaviour INSTANCE = new InventoryTransactionEventBridgeBehaviour();

    private InventoryTransactionEventBridgeBehaviour() {
    }

    public static InventoryTransactionEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isCancelled(Object transactionType, Object inventory, Object item) {
        Object bukkitServer = Bukkit.getServer();
        Object pluginManager = BridgeReflection.invoke(bukkitServer, "getPluginManager");
        try {
            Class<?> typeClass = Class.forName("org.bukkit.event.inventory.InventoryTransactionType");
            Class<?> inventoryClass = Class.forName("org.bukkit.inventory.Inventory");
            Class<?> itemClass = Class.forName("org.bukkit.inventory.ItemStack");
            Class<?> eventClass = Class.forName("org.bukkit.event.inventory.InventoryTransactionEvent");
            Object event = eventClass
                    .getConstructor(typeClass, inventoryClass, itemClass)
                    .newInstance(transactionType, inventory, item);
            BridgeReflection.invoke(pluginManager, "callEvent", event);
            return (Boolean) BridgeReflection.invoke(event, "isCancelled");
        } catch (Exception exception) {
            throw new IllegalStateException("Unable to dispatch inventory transaction event", exception);
        }
    }
}
