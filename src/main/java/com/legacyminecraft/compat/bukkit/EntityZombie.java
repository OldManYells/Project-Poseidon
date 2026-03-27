package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat zombie entity alias.
 */
public class EntityZombie extends EntityMonster implements Zombie {
    public EntityZombie() {
    }

    public EntityZombie(WorldServer world) {
        this.world = world;
    }
}
