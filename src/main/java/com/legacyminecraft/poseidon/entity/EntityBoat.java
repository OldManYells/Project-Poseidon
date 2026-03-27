package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local boat scaffold.
 */
public class EntityBoat extends Entity {
    public int c = 1;
    public int b;
    public int damage;
    public double maxSpeed = 0.4D;

    public EntityBoat() {
    }

    public EntityBoat(World world, double x, double y, double z) {
        this.world = world;
        this.locX = x;
        this.locY = y;
        this.locZ = z;
    }

    public void poseidonMarkDamaged() {
    }

    public double poseidonPassengerYOffset() {
        return 0.0D;
    }

    public void poseidonSuperCollide(Entity other) {
    }

    public void a(int itemId, int count, float offsetY) {
    }
}
