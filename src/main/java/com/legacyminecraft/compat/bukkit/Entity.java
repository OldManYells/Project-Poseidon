package com.legacyminecraft.compat.bukkit;

import java.util.UUID;

/**
 * Canonical compat entity scaffold.
 */
public class Entity implements Player {
    public int id;
    public WorldServer world;
    public double locX;
    public double locY;
    public double locZ;
    public double motX;
    public double motY;
    public double motZ;
    public float yaw;
    public float pitch;
    public float fallDistance;
    public int fireTicks;
    public int health = 20;
    public boolean velocityChanged;
    public boolean dead;
    public Entity vehicle;
    public Entity passenger;
    public AxisAlignedBB boundingBox = new AxisAlignedBB();
    private final UUID uniqueId = new UUID(0L, 0L);

    public Entity getHandle() {
        return this;
    }

    public Entity getBukkitEntity() {
        return this;
    }

    public void setLocation(double x, double y, double z, float yaw, float pitch) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.yaw = yaw;
        this.pitch = pitch;
    }

    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        setLocation(x, y, z, yaw, pitch);
    }

    public void setPassengerOf(Entity entity) {
        this.vehicle = entity;
        if (entity != null) {
            entity.passenger = this;
        }
    }

    public void die() {
        this.dead = true;
    }

    public Location getLocation() {
        World worldHandle = this.world == null ? new WorldServer() : this.world;
        return new Location(worldHandle.getWorld(), this.locX, this.locY, this.locZ);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }
}
