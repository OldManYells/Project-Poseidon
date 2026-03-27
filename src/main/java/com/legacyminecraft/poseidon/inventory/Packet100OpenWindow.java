package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local open-window packet alias.
 */
public class Packet100OpenWindow extends com.legacyminecraft.compat.bukkit.Packet100OpenWindow {
    public Packet100OpenWindow(int windowId, int type, String title, int size) {
        super(windowId, type, title, size);
    }
}
