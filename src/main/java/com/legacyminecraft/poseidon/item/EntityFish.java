package com.legacyminecraft.poseidon.item;

/**
 * Item-local fishing hook entity scaffold.
 */
public class EntityFish extends Entity {
    public EntityFish(World world, EntityHuman owner) {
        this.world = world.toCompatWorldServer();
        this.locX = owner.locX;
        this.locY = owner.locY;
        this.locZ = owner.locZ;
    }

    public int h() {
        return 0;
    }
}
