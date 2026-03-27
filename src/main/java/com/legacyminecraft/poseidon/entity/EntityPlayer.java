package com.legacyminecraft.poseidon.entity;

/**
 * Canonical player-entity scaffold.
 */
public class EntityPlayer extends EntityHuman {
    public double lastY;
    public double lastZ;
    public float lastYaw;
    public float lastPitch;
    public float br;
    private boolean sleeping;

    public EntityPlayer() {
        this.height = 1.62F;
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.boundingBox.minX = x - 0.30000001192092896D;
        this.boundingBox.minY = y;
        this.boundingBox.minZ = z - 0.30000001192092896D;
        this.boundingBox.maxX = x + 0.30000001192092896D;
        this.boundingBox.maxY = y + (double) this.height;
        this.boundingBox.maxZ = z + 0.30000001192092896D;
    }

    public void move(double deltaX, double deltaY, double deltaZ) {
        this.lastX = this.locX;
        this.lastY = this.locY;
        this.lastZ = this.locZ;
        this.locX += deltaX;
        this.locY += deltaY;
        this.locZ += deltaZ;
        this.boundingBox = this.boundingBox.b(deltaX, deltaY, deltaZ);
    }

    public void a(boolean resetFallDistance) {
        this.lastYaw = this.yaw;
        this.lastPitch = this.pitch;
        if (resetFallDistance) {
            this.fallDistance = 0.0F;
        }
    }

    public boolean isSleeping() {
        return sleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.sleeping = sleeping;
    }
}
