package com.legacyminecraft.poseidon.world;

/**
 * Canonical entity model scaffold used while moving NMS logic into Poseidon.
 * Legacy wrappers are expected to bridge to this shape.
 */
public class Entity {
    public int id;
    public double locX;
    public double locY;
    public double locZ;
    public double motX;
    public double motY;
    public double motZ;
    public float length;
    public float yaw;
    public float pitch;
    public boolean dead;
    public AxisAlignedBB boundingBox = AxisAlignedBB.a(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
    public World world;
    public Entity vehicle;
    public Entity passenger;
    public boolean bG;
    public int bH;
    public int bI;
    public int bJ;
    public boolean velocityChanged;

    public void move(double x, double y, double z) {
        this.locX += x;
        this.locY += y;
        this.locZ += z;
        if (this.boundingBox != null) {
            this.boundingBox = this.boundingBox.c(x, y, z);
        }
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public boolean damageEntity(Entity source, int damage) {
        return damage > 0;
    }

    public void mount(Entity vehicle) {
        this.vehicle = vehicle;
    }

    public void die() {
        this.dead = true;
    }

    public double f(double x, double y, double z) {
        double deltaX = this.locX - x;
        double deltaY = this.locY - y;
        double deltaZ = this.locZ - z;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }

    public Object getBukkitEntity() {
        return null;
    }
}
