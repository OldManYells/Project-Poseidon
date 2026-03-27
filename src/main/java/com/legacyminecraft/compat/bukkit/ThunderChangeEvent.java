package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat thunder-change event scaffold.
 */
public class ThunderChangeEvent {
    private final World world;
    private final boolean toThunderState;
    private boolean cancelled;

    public ThunderChangeEvent(World world, boolean toThunderState) {
        this.world = world;
        this.toThunderState = toThunderState;
    }

    public World getWorld() {
        return world;
    }

    public boolean toThunderState() {
        return toThunderState;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
