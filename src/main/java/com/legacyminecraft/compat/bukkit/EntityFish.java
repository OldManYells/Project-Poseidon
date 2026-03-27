package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat fish-hook entity scaffold.
 */
public class EntityFish extends Entity implements Fish {
    public EntityHuman owner;

    public EntityFish() {
    }

    public EntityFish(WorldServer world) {
        this.world = world;
    }
}
