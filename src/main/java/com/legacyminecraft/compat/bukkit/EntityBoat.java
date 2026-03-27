package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat boat entity scaffold.
 */
public class EntityBoat extends Entity implements Boat {
    public double maxSpeed = 0.4D;

    public EntityBoat() {
    }

    public EntityBoat(WorldServer world, double x, double y, double z) {
        this.world = world;
        this.setLocation(x, y, z, 0.0F, 0.0F);
    }
}
