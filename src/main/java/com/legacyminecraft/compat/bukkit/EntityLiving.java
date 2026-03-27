package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat living-entity scaffold.
 */
public class EntityLiving extends Entity implements LivingEntity {
    public void setPosition(double x, double y, double z) {
        setLocation(x, y, z, this.yaw, this.pitch);
    }
}
