package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat player-interact-entity event scaffold.
 */
public class PlayerInteractEntityEvent extends Event {
    private final Player player;
    private final com.legacyminecraft.compat.bukkit.entity.Entity rightClicked;

    public PlayerInteractEntityEvent(Player player, com.legacyminecraft.compat.bukkit.entity.Entity rightClicked) {
        this.player = player;
        this.rightClicked = rightClicked;
    }

    public Player getPlayer() {
        return player;
    }

    public com.legacyminecraft.compat.bukkit.entity.Entity getRightClicked() {
        return rightClicked;
    }
}

