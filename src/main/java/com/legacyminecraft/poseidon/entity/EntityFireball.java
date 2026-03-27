package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local fireball scaffold.
 */
public class EntityFireball extends Entity {
    public EntityLiving shooter;
    public double c;
    public double d;
    public double e;

    public EntityFireball() {
    }

    public EntityFireball(World world) {
        this.world = world;
    }

    public EntityFireball(World world, EntityLiving shooter, double directionX, double directionY, double directionZ) {
        this.world = world;
        this.shooter = shooter;
        this.c = directionX;
        this.d = directionY;
        this.e = directionZ;
    }

    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        setPosition(x, y, z);
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setDirection(double x, double y, double z) {
        this.c = x;
        this.d = y;
        this.e = z;
    }
}
