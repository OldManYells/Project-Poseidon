package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local snowball scaffold.
 */
public class EntitySnowball extends Entity {
    public EntitySnowball() {
    }

    public EntitySnowball(World world, double x, double y, double z) {
        this.world = world;
        this.setPosition(x, y, z);
    }
}
