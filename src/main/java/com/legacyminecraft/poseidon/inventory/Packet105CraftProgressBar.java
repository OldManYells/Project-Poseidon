package com.legacyminecraft.poseidon.inventory;

/**
 * Inventory-local progress-bar packet alias.
 */
public class Packet105CraftProgressBar extends com.legacyminecraft.compat.bukkit.Packet105CraftProgressBar {
    public Packet105CraftProgressBar(int windowId, int progressBar, int value) {
        super(windowId, progressBar, value);
    }
}
