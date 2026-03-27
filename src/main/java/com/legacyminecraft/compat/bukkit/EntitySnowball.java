package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat snowball entity scaffold.
 */
public class EntitySnowball extends Entity implements Snowball {
    public EntityLiving shooter;

    public EntitySnowball() {
    }

    public EntitySnowball(WorldServer world, double x, double y, double z) {
        this.world = world;
        this.setLocation(x, y, z, 0.0F, 0.0F);
    }

    public EntitySnowball(World world, EntityLiving shooter) {
        this.world = world instanceof WorldServer ? (WorldServer) world : null;
        this.shooter = shooter;
    }
}
