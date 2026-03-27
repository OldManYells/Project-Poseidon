package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat spider entity alias.
 */
public class EntitySpider extends EntityLiving implements Spider {
    public EntitySpider() {
    }

    public EntitySpider(WorldServer world) {
        this.world = world;
    }
}
