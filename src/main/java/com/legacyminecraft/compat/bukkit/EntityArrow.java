package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat arrow entity scaffold.
 */
public class EntityArrow extends Entity implements Arrow {
    public EntityLiving shooter;

    public EntityArrow() {
    }

    public EntityArrow(WorldServer world) {
        this.world = world;
    }

    public EntityArrow(World world, EntityLiving shooter) {
        this.world = world instanceof WorldServer ? (WorldServer) world : null;
        this.shooter = shooter;
    }

    public void setPositionRotation(double x, double y, double z, float yaw, float pitch) {
        this.setLocation(x, y, z, yaw, pitch);
    }

    public void a(double velocityX, double velocityY, double velocityZ, float speed, float spread) {
    }
}
