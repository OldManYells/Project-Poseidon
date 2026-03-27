package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local falling-sand scaffold.
 */
public class EntityFallingSand extends Entity {
    public int a;
    public boolean aI;
    public int b;
    public double lastX;
    public double lastY;
    public double lastZ;

    public EntityFallingSand() {
    }

    public EntityFallingSand(World world, double x, double y, double z, int blockId) {
        this.world = world;
        this.setPosition(x, y, z);
        this.a = blockId;
    }

    public void poseidonInitializeBounds() {
        this.boundingBox = new com.legacyminecraft.compat.bukkit.AxisAlignedBB();
    }
}
