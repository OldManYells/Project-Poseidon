package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat cow entity alias.
 */
public class EntityCow extends EntityAnimal implements Cow {
    public EntityCow() {
    }

    public EntityCow(WorldServer world) {
        this.world = world;
    }
}
