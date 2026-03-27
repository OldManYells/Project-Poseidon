package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local toggle-sneak event scaffold.
 */
public class PlayerToggleSneakEvent {
    private final Player player;
    private final boolean sneaking;
    private boolean cancelled;

    public PlayerToggleSneakEvent(Player player, boolean sneaking) {
        this.player = player;
        this.sneaking = sneaking;
    }

    public Player getPlayer() {
        return player;
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
