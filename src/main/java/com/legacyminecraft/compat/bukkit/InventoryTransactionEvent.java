package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat inventory transaction event scaffold.
 */
public class InventoryTransactionEvent {
    private final InventoryTransactionType transactionType;
    private final Inventory inventory;
    private final ItemStack item;
    private boolean cancelled;

    public InventoryTransactionEvent(InventoryTransactionType transactionType, Inventory inventory, ItemStack item) {
        this.transactionType = transactionType;
        this.inventory = inventory;
        this.item = item;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
