package com.legacyminecraft.poseidon.entity;

/**
 * Mutable state snapshot used by canonical entity tracker frame processing.
 */
public final class EntityTrackingState {
    private int encodedX;
    private int encodedY;
    private int encodedZ;
    private int encodedYaw;
    private int encodedPitch;
    private int teleportCounter;
    private double motionX;
    private double motionY;
    private double motionZ;

    public EntityTrackingState(
            int encodedX,
            int encodedY,
            int encodedZ,
            int encodedYaw,
            int encodedPitch,
            int teleportCounter,
            double motionX,
            double motionY,
            double motionZ
    ) {
        this.encodedX = encodedX;
        this.encodedY = encodedY;
        this.encodedZ = encodedZ;
        this.encodedYaw = encodedYaw;
        this.encodedPitch = encodedPitch;
        this.teleportCounter = teleportCounter;
        this.motionX = motionX;
        this.motionY = motionY;
        this.motionZ = motionZ;
    }

    public int getEncodedX() {
        return encodedX;
    }

    public void setEncodedX(int encodedX) {
        this.encodedX = encodedX;
    }

    public int getEncodedY() {
        return encodedY;
    }

    public void setEncodedY(int encodedY) {
        this.encodedY = encodedY;
    }

    public int getEncodedZ() {
        return encodedZ;
    }

    public void setEncodedZ(int encodedZ) {
        this.encodedZ = encodedZ;
    }

    public int getEncodedYaw() {
        return encodedYaw;
    }

    public void setEncodedYaw(int encodedYaw) {
        this.encodedYaw = encodedYaw;
    }

    public int getEncodedPitch() {
        return encodedPitch;
    }

    public void setEncodedPitch(int encodedPitch) {
        this.encodedPitch = encodedPitch;
    }

    public int getTeleportCounter() {
        return teleportCounter;
    }

    public void setTeleportCounter(int teleportCounter) {
        this.teleportCounter = teleportCounter;
    }

    public double getMotionX() {
        return motionX;
    }

    public void setMotionX(double motionX) {
        this.motionX = motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public void setMotionY(double motionY) {
        this.motionY = motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public void setMotionZ(double motionZ) {
        this.motionZ = motionZ;
    }
}
