package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat creature-spawn event scaffold.
 */
public class CreatureSpawnEvent extends Event {
    private final com.legacyminecraft.compat.bukkit.Entity entity;
    private final CreatureType creatureType;
    private final Location location;
    private final SpawnReason spawnReason;

    public CreatureSpawnEvent(
            com.legacyminecraft.compat.bukkit.Entity entity,
            CreatureType creatureType,
            Location location,
            SpawnReason spawnReason
    ) {
        this.entity = entity;
        this.creatureType = creatureType;
        this.location = location;
        this.spawnReason = spawnReason;
    }

    public com.legacyminecraft.compat.bukkit.Entity getEntity() {
        return entity;
    }

    public CreatureType getCreatureType() {
        return creatureType;
    }

    public Location getLocation() {
        return location;
    }

    public SpawnReason getSpawnReason() {
        return spawnReason;
    }

    public enum SpawnReason {
        CUSTOM,
        LIGHTNING,
        NATURAL,
        BED,
        DEFAULT
    }
}
