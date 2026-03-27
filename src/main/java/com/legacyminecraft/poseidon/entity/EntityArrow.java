package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local arrow scaffold.
 */
public class EntityArrow extends Entity {
    public EntityLiving shooter;

    public EntityArrow() {
    }

    public EntityArrow(World world, EntityLiving shooter) {
        this.world = world;
        this.shooter = shooter;
    }

    public void a(double x, double y, double z, float speed, float spread) {
    }
}
