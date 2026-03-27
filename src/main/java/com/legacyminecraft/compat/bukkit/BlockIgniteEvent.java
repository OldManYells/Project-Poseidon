package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat ignite-event scaffold.
 */
public class BlockIgniteEvent {
    public enum IgniteCause {
        LIGHTNING
    }

    private boolean cancelled;

    public BlockIgniteEvent(Object block, IgniteCause cause, Object igniter) {
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
