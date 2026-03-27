package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local creature scaffold.
 */
public class EntityCreature extends EntityLiving {
    public PathEntity pathEntity;

    public EntityCreature() {
    }

    public EntityCreature(World world) {
        this.world = world;
    }

    public void setPathEntity(PathEntity pathEntity) {
        this.pathEntity = pathEntity;
    }

    public boolean C() {
        return false;
    }
}
