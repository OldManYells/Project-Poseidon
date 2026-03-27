package com.legacyminecraft.poseidon.item;

/**
 * Item-behaviour player item damage event scaffold.
 */
public class PlayerItemDamageEvent {
    private final Player player;
    private int damage;
    private boolean cancelled;

    public PlayerItemDamageEvent(Player player, Object itemStack, int damage) {
        this.player = player;
        this.damage = damage;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDamage() {
        return damage;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
