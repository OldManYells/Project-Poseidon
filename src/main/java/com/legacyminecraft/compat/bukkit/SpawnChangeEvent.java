package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat spawn-change event scaffold.
 */
public class SpawnChangeEvent {
    private final World world;
    private final Location previousLocation;

    public SpawnChangeEvent(World world, Location previousLocation) {
        this.world = world;
        this.previousLocation = previousLocation;
    }

    public World getWorld() {
        return world;
    }

    public Location getPreviousLocation() {
        return previousLocation;
    }
}
