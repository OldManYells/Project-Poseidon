package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat egg entity scaffold.
 */
public class EntityEgg extends Entity implements Egg {
    public EntityLiving thrower;

    public EntityEgg() {
    }

    public EntityEgg(WorldServer world, double x, double y, double z) {
        this.world = world;
        this.setLocation(x, y, z, 0.0F, 0.0F);
    }

    public EntityEgg(World world, EntityLiving thrower) {
        this.world = world instanceof WorldServer ? (WorldServer) world : null;
        this.thrower = thrower;
    }
}
