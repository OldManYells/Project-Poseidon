package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local egg scaffold.
 */
public class EntityEgg extends Entity {
    public EntityEgg() {
    }

    public EntityEgg(World world, double x, double y, double z) {
        this.world = world;
        this.setPosition(x, y, z);
    }
}
