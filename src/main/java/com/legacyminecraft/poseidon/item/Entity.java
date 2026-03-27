package com.legacyminecraft.poseidon.item;

/**
 * Item-local entity alias.
 */
public class Entity extends com.legacyminecraft.compat.bukkit.Entity {
    public float I() {
        return 0.0F;
    }

    public void setPosition(double x, double y, double z) {
        this.locX = x;
        this.locY = y;
        this.locZ = z;
    }
}
