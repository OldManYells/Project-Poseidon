package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local primed TNT scaffold.
 */
public class EntityTNTPrimed extends Entity {
    public EntityTNTPrimed() {
    }

    public EntityTNTPrimed(World world, double x, double y, double z) {
        this.world = world;
        this.setPosition(x, y, z);
    }
}
