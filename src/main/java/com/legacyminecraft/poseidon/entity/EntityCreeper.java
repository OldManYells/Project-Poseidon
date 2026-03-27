package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local creeper scaffold.
 */
public class EntityCreeper extends EntityMonster {
    private boolean powered;

    public EntityCreeper() {
    }

    public EntityCreeper(World world) {
        super(world);
    }

    public boolean isPowered() {
        return powered;
    }

    public void setPowered(boolean powered) {
        this.powered = powered;
    }

    public void b(int itemId, int amount) {
    }
}
