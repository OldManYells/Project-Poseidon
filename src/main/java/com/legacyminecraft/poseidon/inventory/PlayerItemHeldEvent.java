package com.legacyminecraft.poseidon.inventory;

import com.legacyminecraft.compat.bukkit.Player;

/**
 * Inventory-local player hotbar-selection event scaffold.
 */
public class PlayerItemHeldEvent {
    private final Player player;
    private final int previousSlot;
    private final int newSlot;

    public PlayerItemHeldEvent(Player player, int previousSlot, int newSlot) {
        this.player = player;
        this.previousSlot = previousSlot;
        this.newSlot = newSlot;
    }

    public Player getPlayer() {
        return player;
    }

    public int getPreviousSlot() {
        return previousSlot;
    }

    public int getNewSlot() {
        return newSlot;
    }
}
