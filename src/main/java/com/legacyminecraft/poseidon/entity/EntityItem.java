package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local dropped-item scaffold.
 */
public class EntityItem extends Entity {
    public ItemStack itemStack;
    public int pickupDelay;
    public int age;
    public int health = 5;
    public int lastTick;

    public EntityItem() {
    }

    public EntityItem(World world, double x, double y, double z, ItemStack itemStack) {
        this.world = world;
        this.locX = x;
        this.locY = y;
        this.locZ = z;
        this.itemStack = itemStack;
    }

    public void poseidonInitializeBounds() {
        this.boundingBox = new com.legacyminecraft.compat.bukkit.AxisAlignedBB();
    }

    public ItemStack poseidonGetItemStack() {
        return itemStack;
    }

    public void poseidonSetItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
