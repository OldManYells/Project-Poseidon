package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local transaction packet alias.
 */
public class Packet106Transaction extends com.legacyminecraft.compat.bukkit.Packet106Transaction {
    public Packet106Transaction(int windowId, short actionNumber, boolean accepted) {
        super(windowId, actionNumber, accepted);
    }
}
