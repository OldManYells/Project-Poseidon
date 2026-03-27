package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat primed-TNT entity alias.
 */
public class EntityTNTPrimed extends Entity implements TNTPrimed {
    public float yield = 4.0F;
    public boolean isIncendiary;
    public int fuseTicks = 80;

    public EntityTNTPrimed() {
    }

    public EntityTNTPrimed(WorldServer world, double x, double y, double z) {
        this.world = world;
        this.setLocation(x, y, z, 0.0F, 0.0F);
    }
}
