package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat event base scaffold.
 */
public class Event {
    private boolean cancelled;

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum Result {
        DEFAULT,
        ALLOW,
        DENY
    }
}
