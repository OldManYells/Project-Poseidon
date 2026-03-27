package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat entity-item scaffold.
 */
public class EntityItem extends Entity {
    public ItemStack itemStack;
    public int pickupDelay;

    public EntityItem() {
    }

    public EntityItem(WorldServer world, double x, double y, double z, ItemStack itemStack) {
        this.world = world;
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.itemStack = itemStack;
    }
}
