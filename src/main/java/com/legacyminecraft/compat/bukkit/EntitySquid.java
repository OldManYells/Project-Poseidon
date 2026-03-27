package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat squid entity alias.
 */
public class EntitySquid extends EntityWaterAnimal implements Squid {
    public EntitySquid() {
    }

    public EntitySquid(WorldServer world) {
        this.world = world;
    }
}
