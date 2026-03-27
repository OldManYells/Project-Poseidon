package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat combust-event scaffold.
 */
public class EntityCombustEvent {
    private boolean cancelled;

    public EntityCombustEvent(Object entity) {
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
