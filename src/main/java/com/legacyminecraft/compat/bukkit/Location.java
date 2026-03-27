package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat location scaffold.
 */
public class Location {
    private Object world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;

    public Location(Object world, double x, double y, double z) {
        this(world, x, y, z, 0.0F, 0.0F);
    }

    public Location(Object world, double x, double y, double z, float yaw, float pitch) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public static int locToBlock(double value) {
        return (int) Math.floor(value);
    }

    public Object getWorld() {
        return world;
    }

    public void setWorld(Object world) {
        this.world = world;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public Vector getDirection() {
        double yawRadians = Math.toRadians(yaw);
        double pitchRadians = Math.toRadians(pitch);
        double xComponent = -Math.sin(yawRadians) * Math.cos(pitchRadians);
        double yComponent = -Math.sin(pitchRadians);
        double zComponent = Math.cos(yawRadians) * Math.cos(pitchRadians);
        return new Vector(xComponent, yComponent, zComponent);
    }

    @Override
    public Location clone() {
        return new Location(world, x, y, z, yaw, pitch);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Location)) {
            return false;
        }
        Location location = (Location) other;
        return location.world == this.world
                && Double.compare(location.x, this.x) == 0
                && Double.compare(location.y, this.y) == 0
                && Double.compare(location.z, this.z) == 0
                && Float.compare(location.yaw, this.yaw) == 0
                && Float.compare(location.pitch, this.pitch) == 0;
    }

    public int getBlockX() {
        return (int) Math.floor(x);
    }

    public int getBlockY() {
        return (int) Math.floor(y);
    }

    public int getBlockZ() {
        return (int) Math.floor(z);
    }

    public double distance(Location other) {
        if (other == null) {
            return Double.NaN;
        }
        double deltaX = this.x - other.x;
        double deltaY = this.y - other.y;
        double deltaZ = this.z - other.z;
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    }
}
