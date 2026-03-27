package com.legacyminecraft.poseidon.world.biome;

/**
 * Spawn entry scaffold for biome mob tables.
 */
public class BiomeMeta {
    private final Class entityClass;
    private final int spawnWeight;

    public BiomeMeta() {
        this(Object.class, 1);
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
