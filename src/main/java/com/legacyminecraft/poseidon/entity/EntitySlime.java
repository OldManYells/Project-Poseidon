package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local slime scaffold.
 */
public class EntitySlime extends EntityLiving implements IAnimal {
    private int size = 1;

    public EntitySlime() {
    }

    public EntitySlime(World world) {
        this.world = world;
    }

    public void setSize(int size) {
        this.size = Math.max(1, size);
    }

    public int getSize() {
        return size;
    }

    public boolean e(EntityHuman human) {
        return human != null;
    }

    public float f(EntityHuman human) {
        return human == null ? Float.MAX_VALUE : (float) Math.sqrt(this.g(human));
    }
}
