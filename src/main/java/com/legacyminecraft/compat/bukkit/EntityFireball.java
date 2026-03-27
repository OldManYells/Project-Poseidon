package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat fireball entity scaffold.
 */
public class EntityFireball extends Entity implements Fireball {
    public EntityLiving shooter;
    public double c;
    public double d;
    public double e;

    public EntityFireball() {
    }

    public EntityFireball(WorldServer world) {
        this.world = world;
    }

    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        this.setLocation(x, y, z, yaw, pitch);
    }

    public void setDirection(double x, double y, double z) {
        this.c = x;
        this.d = y;
        this.e = z;
    }
}
