package com.legacyminecraft.poseidon.runtime;

/**
 * Runtime-local preprocess event scaffold.
 */
public class PlayerCommandPreprocessEvent {
    private final Player player;
    private String message;
    private boolean cancelled;

    public PlayerCommandPreprocessEvent(Player player, String message) {
        this.player = player;
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
