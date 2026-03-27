package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local biome spawn entry scaffold.
 */
public class BiomeMeta {
    private final Class entityClass;
    private final int spawnWeight;

    public BiomeMeta() {
        this(EntityLiving.class, 1);
    }

    public BiomeMeta(Class entityClass, int spawnWeight) {
        this.entityClass = entityClass;
        this.spawnWeight = spawnWeight;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public int getSpawnWeight() {
        return spawnWeight;
    }
}
