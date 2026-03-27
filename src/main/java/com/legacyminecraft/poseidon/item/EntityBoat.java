package com.legacyminecraft.poseidon.item;

/**
 * Item-local boat entity scaffold.
 */
public class EntityBoat extends Entity {
    public EntityBoat(World world, double x, double y, double z) {
        this.world = world.toCompatWorldServer();
        this.locX = x;
        this.locY = y;
        this.locZ = z;
    }
}
