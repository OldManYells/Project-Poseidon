package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat pig-zombie entity alias.
 */
public class EntityPigZombie extends EntityZombie implements PigZombie {
    public int angerLevel;

    public EntityPigZombie() {
    }

    public EntityPigZombie(WorldServer world) {
        this.world = world;
    }
}
