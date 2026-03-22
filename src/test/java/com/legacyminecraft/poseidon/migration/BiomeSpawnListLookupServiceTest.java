package com.legacyminecraft.poseidon.migration;

import com.legacyminecraft.poseidon.world.biome.BiomeSpawnListLookupBehaviour;
import net.minecraft.server.EnumCreatureType;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class BiomeSpawnListLookupServiceTest {
    @Test
    public void resolveSpawnListMatchesLegacyCreatureTypeRouting() {
        BiomeSpawnListLookupBehaviour service = BiomeSpawnListLookupBehaviour.getInstance();
        List monsterSpawns = new ArrayList();
        List creatureSpawns = new ArrayList();
        List waterCreatureSpawns = new ArrayList();

        Assert.assertSame(monsterSpawns, service.resolveSpawnList(EnumCreatureType.MONSTER, monsterSpawns, creatureSpawns, waterCreatureSpawns));
        Assert.assertSame(creatureSpawns, service.resolveSpawnList(EnumCreatureType.CREATURE, monsterSpawns, creatureSpawns, waterCreatureSpawns));
        Assert.assertSame(waterCreatureSpawns, service.resolveSpawnList(EnumCreatureType.WATER_CREATURE, monsterSpawns, creatureSpawns, waterCreatureSpawns));
        Assert.assertNull(service.resolveSpawnList(null, monsterSpawns, creatureSpawns, waterCreatureSpawns));
    }
}
