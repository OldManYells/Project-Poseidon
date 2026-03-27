package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat falling-sand entity alias.
 */
public class EntityFallingSand extends Entity implements FallingSand {
    public int a;
    public boolean aI;
    public int b;
    public double lastX;
    public double lastY;
    public double lastZ;

    public EntityFallingSand() {
    }

    public EntityFallingSand(WorldServer world, double x, double y, double z, int typeId) {
        this.world = world;
        this.setLocation(x, y, z, 0.0F, 0.0F);
        this.a = typeId;
    }
}
