package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local close-window packet alias.
 */
public class Packet101CloseWindow extends com.legacyminecraft.compat.bukkit.Packet101CloseWindow {
    public Packet101CloseWindow(int windowId) {
        super(windowId);
    }
}
