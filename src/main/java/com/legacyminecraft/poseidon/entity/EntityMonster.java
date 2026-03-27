package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local monster scaffold.
 */
public class EntityMonster extends EntityCreature {
    public int attackTicks;

    public EntityMonster() {
    }

    public EntityMonster(World world) {
        super(world);
    }
}
