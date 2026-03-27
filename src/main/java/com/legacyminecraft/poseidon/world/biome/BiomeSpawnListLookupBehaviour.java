package com.legacyminecraft.poseidon.world.biome;


import java.util.List;

/**
 * Canonical spawn-list lookup policy for biome wrappers.
 */
public final class BiomeSpawnListLookupBehaviour {
    private static final BiomeSpawnListLookupBehaviour INSTANCE = new BiomeSpawnListLookupBehaviour();

    private BiomeSpawnListLookupBehaviour() {
    }

    public static BiomeSpawnListLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public List resolveSpawnList(EnumCreatureType creatureType, List monsterSpawns, List creatureSpawns, List waterCreatureSpawns) {
        if (creatureType == EnumCreatureType.MONSTER) {
            return monsterSpawns;
        }

        if (creatureType == EnumCreatureType.CREATURE) {
            return creatureSpawns;
        }

        if (creatureType == EnumCreatureType.WATER_CREATURE) {
            return waterCreatureSpawns;
        }

        return null;
    }
}
