package com.legacyminecraft.poseidon.compat.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryTransactionEvent;
import org.bukkit.event.inventory.InventoryTransactionType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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

    public boolean isCancelled(InventoryTransactionType transactionType, Inventory inventory, ItemStack item) {
        InventoryTransactionEvent event = new InventoryTransactionEvent(transactionType, inventory, item);
        Bukkit.getServer().getPluginManager().callEvent(event);
        return event.isCancelled();
    }
}
