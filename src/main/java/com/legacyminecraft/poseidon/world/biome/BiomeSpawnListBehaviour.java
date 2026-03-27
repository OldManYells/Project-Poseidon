package com.legacyminecraft.poseidon.world.biome;


import java.util.List;

/**
 * Canonical biome spawn-list configuration service.
 */
public final class BiomeSpawnListBehaviour {
    private static final BiomeSpawnListBehaviour INSTANCE = new BiomeSpawnListBehaviour();

    private BiomeSpawnListBehaviour() {
    }

    public static BiomeSpawnListBehaviour getInstance() {
        return INSTANCE;
    }

    public void configureSkyBiomeSpawns(List monsterSpawns, List creatureSpawns, List waterCreatureSpawns) {
        monsterSpawns.clear();
        creatureSpawns.clear();
        waterCreatureSpawns.clear();
        creatureSpawns.add(new BiomeMeta(EntityChicken.class, 10));
    }

    public void configureForestBiomeSpawns(List creatureSpawns) {
        creatureSpawns.add(new BiomeMeta(EntityWolf.class, 2));
    }

    public void configureTaigaBiomeSpawns(List creatureSpawns) {
        creatureSpawns.add(new BiomeMeta(EntityWolf.class, 2));
    }

    public void configureHellBiomeSpawns(List monsterSpawns, List creatureSpawns, List waterCreatureSpawns) {
        monsterSpawns.clear();
        creatureSpawns.clear();
        waterCreatureSpawns.clear();
        monsterSpawns.add(new BiomeMeta(EntityGhast.class, 10));
        monsterSpawns.add(new BiomeMeta(EntityPigZombie.class, 10));
    }
}
