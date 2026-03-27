package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat tame event scaffold.
 */
public class EntityTameEvent extends Event {
    private final com.legacyminecraft.compat.bukkit.Entity entity;
    private final AnimalTamer owner;

    public EntityTameEvent(com.legacyminecraft.compat.bukkit.Entity entity, AnimalTamer owner) {
        this.entity = entity;
        this.owner = owner;
    }

    public com.legacyminecraft.compat.bukkit.Entity getEntity() {
        return entity;
    }

    public AnimalTamer getOwner() {
        return owner;
    }
}
