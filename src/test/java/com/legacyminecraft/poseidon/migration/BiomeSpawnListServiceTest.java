package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.biome.BiomeSpawnListBehaviour;
import net.minecraft.server.BiomeMeta;
import net.minecraft.server.EntityChicken;
import net.minecraft.server.EntityCow;
import net.minecraft.server.EntityGhast;
import net.minecraft.server.EntityPigZombie;
import net.minecraft.server.EntitySpider;
import net.minecraft.server.EntitySquid;
import net.minecraft.server.EntityWolf;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BiomeSpawnListServiceTest {
    @Test
    public void configureSkyBiomeSpawnsResetsLegacyListsToSkyDefaults() {
        BiomeSpawnListBehaviour service = BiomeSpawnListBehaviour.getInstance();
        List monsterSpawns = new ArrayList();
        List creatureSpawns = new ArrayList();
        List waterCreatureSpawns = new ArrayList();

        monsterSpawns.add(new BiomeMeta(EntitySpider.class, 99));
        creatureSpawns.add(new BiomeMeta(EntityCow.class, 99));
        waterCreatureSpawns.add(new BiomeMeta(EntitySquid.class, 99));

        service.configureSkyBiomeSpawns(monsterSpawns, creatureSpawns, waterCreatureSpawns);

        Assert.assertTrue(monsterSpawns.isEmpty());
        Assert.assertTrue(waterCreatureSpawns.isEmpty());
        Assert.assertEquals(1, creatureSpawns.size());

        BiomeMeta skyEntry = (BiomeMeta) creatureSpawns.get(0);
        Assert.assertEquals(EntityChicken.class, skyEntry.a);
        Assert.assertEquals(10, skyEntry.b);
    }

    @Test
    public void configureForestBiomeSpawnsAddsWolfEntry() {
        BiomeSpawnListBehaviour service = BiomeSpawnListBehaviour.getInstance();
        List creatureSpawns = new ArrayList();

        service.configureForestBiomeSpawns(creatureSpawns);

        Assert.assertEquals(1, creatureSpawns.size());
        BiomeMeta wolfEntry = (BiomeMeta) creatureSpawns.get(0);
        Assert.assertEquals(EntityWolf.class, wolfEntry.a);
        Assert.assertEquals(2, wolfEntry.b);
    }

    @Test
    public void configureTaigaBiomeSpawnsAddsWolfEntry() {
        BiomeSpawnListBehaviour service = BiomeSpawnListBehaviour.getInstance();
        List creatureSpawns = new ArrayList();

        service.configureTaigaBiomeSpawns(creatureSpawns);

        Assert.assertEquals(1, creatureSpawns.size());
        BiomeMeta wolfEntry = (BiomeMeta) creatureSpawns.get(0);
        Assert.assertEquals(EntityWolf.class, wolfEntry.a);
        Assert.assertEquals(2, wolfEntry.b);
    }

    @Test
    public void configureHellBiomeSpawnsResetsListsToHellDefaults() {
        BiomeSpawnListBehaviour service = BiomeSpawnListBehaviour.getInstance();
        List monsterSpawns = new ArrayList();
        List creatureSpawns = new ArrayList();
        List waterCreatureSpawns = new ArrayList();

        creatureSpawns.add(new BiomeMeta(EntityCow.class, 1));
        waterCreatureSpawns.add(new BiomeMeta(EntitySquid.class, 1));

        service.configureHellBiomeSpawns(monsterSpawns, creatureSpawns, waterCreatureSpawns);

        Assert.assertEquals(2, monsterSpawns.size());
        Assert.assertTrue(creatureSpawns.isEmpty());
        Assert.assertTrue(waterCreatureSpawns.isEmpty());

        BiomeMeta firstMonster = (BiomeMeta) monsterSpawns.get(0);
        BiomeMeta secondMonster = (BiomeMeta) monsterSpawns.get(1);
        Assert.assertEquals(EntityGhast.class, firstMonster.a);
        Assert.assertEquals(10, firstMonster.b);
        Assert.assertEquals(EntityPigZombie.class, secondMonster.a);
        Assert.assertEquals(10, secondMonster.b);
    }
}
