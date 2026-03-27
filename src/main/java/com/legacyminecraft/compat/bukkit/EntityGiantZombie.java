package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat giant-zombie alias.
 */
public class EntityGiantZombie extends EntityMonster implements Giant {
    public EntityGiantZombie() {
    }

    public EntityGiantZombie(WorldServer world) {
        this.world = world;
    }
}
