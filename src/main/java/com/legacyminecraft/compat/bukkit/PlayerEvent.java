package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat player-event base.
 */
public class PlayerEvent extends Event {
    private final Player player;

    public PlayerEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
