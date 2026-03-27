package com.legacyminecraft.poseidon.world.biome;


/**
 * Canonical compatibility behaviour for legacy biome spawn entries.
 */
public final class BiomeSpawnEntryBehaviour {
    private static final BiomeSpawnEntryBehaviour INSTANCE = new BiomeSpawnEntryBehaviour();

    private BiomeSpawnEntryBehaviour() {
    }

    public static BiomeSpawnEntryBehaviour getInstance() {
        return INSTANCE;
    }

    public void initializeLegacyEntry(BiomeMeta entry, Class entityClass, int spawnWeight) {
        entry.poseidonSetEntityClass(entityClass);
        entry.poseidonSetSpawnWeight(spawnWeight);
    }

    public Class resolveEntityClass(BiomeMeta entry) {
        return entry.poseidonGetEntityClass();
    }

    public int resolveSpawnWeight(BiomeMeta entry) {
        return entry.poseidonGetSpawnWeight();
    }
}
