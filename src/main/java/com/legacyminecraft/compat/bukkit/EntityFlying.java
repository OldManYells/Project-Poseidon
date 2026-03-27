package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat flying-entity alias.
 */
public class EntityFlying extends EntityLiving implements Flying {
    public EntityFlying() {
    }

    public EntityFlying(WorldServer world) {
        this.world = world;
    }
}
