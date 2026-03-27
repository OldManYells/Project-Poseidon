package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local player animation event scaffold.
 */
public class PlayerAnimationEvent {
    private final Player player;
    private boolean cancelled;

    public PlayerAnimationEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
