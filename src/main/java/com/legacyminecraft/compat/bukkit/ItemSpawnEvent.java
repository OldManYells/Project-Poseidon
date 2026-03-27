package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat item-spawn event scaffold.
 */
public class ItemSpawnEvent extends Event {
    private final com.legacyminecraft.compat.bukkit.Entity entity;
    private final Location location;

    public ItemSpawnEvent(com.legacyminecraft.compat.bukkit.Entity entity, Location location) {
        this.entity = entity;
        this.location = location;
    }

    public com.legacyminecraft.compat.bukkit.Entity getEntity() {
        return entity;
    }

    public Location getLocation() {
        return location;
    }
}
