package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyBiomeWrapperThinnessTest {
    private static final Path BIOME_SKY_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeSky.java");
    private static final Path BIOME_FOREST_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeForest.java");
    private static final Path BIOME_HELL_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeHell.java");
    private static final Path BIOME_TAIGA_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeTaiga.java");
    private static final Path BIOME_RAINFOREST_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeRainforest.java");
    private static final Path BIOME_BASE_PATH = Paths.get("src/main/java/net/minecraft/server/BiomeBase.java");

    @Test
    public void biomeSkyDelegatesSpawnLogicToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(BIOME_SKY_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeSpawnListBehaviour"));
        Assert.assertTrue(text.contains("configureSkyBiomeSpawns"));
        Assert.assertFalse(text.contains("this.s.clear()"));
        Assert.assertFalse(text.contains("this.t.clear()"));
        Assert.assertFalse(text.contains("this.u.clear()"));
        Assert.assertFalse(text.contains("new BiomeMeta(EntityChicken.class, 10)"));
    }

    @Test
    public void biomeForestDelegatesSpawnAndTreeSelectionLogicToCanonicalServices() throws IOException {
        String text = new String(Files.readAllBytes(BIOME_FOREST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeSpawnListBehaviour"));
        Assert.assertTrue(text.contains("configureForestBiomeSpawns"));
        Assert.assertTrue(text.contains("BiomeTreeGeneratorSelectionBehaviour"));
        Assert.assertTrue(text.contains("selectForestTreeGenerator"));
        Assert.assertFalse(text.contains("new BiomeMeta(EntityWolf.class, 2)"));
        Assert.assertFalse(text.contains("new WorldGenForest()"));
        Assert.assertFalse(text.contains("new WorldGenBigTree()"));
        Assert.assertFalse(text.contains("new WorldGenTrees()"));
    }

    @Test
    public void biomeHellDelegatesSpawnLogicToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(BIOME_HELL_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeSpawnListBehaviour"));
        Assert.assertTrue(text.contains("configureHellBiomeSpawns"));
        Assert.assertFalse(text.contains("this.s.clear()"));
        Assert.assertFalse(text.contains("new BiomeMeta(EntityGhast.class, 10)"));
        Assert.assertFalse(text.contains("new BiomeMeta(EntityPigZombie.class, 10)"));
    }

    @Test
    public void biomeTaigaDelegatesSpawnAndTreeSelectionLogicToCanonicalServices() throws IOException {
        String text = new String(Files.readAllBytes(BIOME_TAIGA_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeSpawnListBehaviour"));
        Assert.assertTrue(text.contains("configureTaigaBiomeSpawns"));
        Assert.assertTrue(text.contains("BiomeTreeGeneratorSelectionBehaviour"));
        Assert.assertTrue(text.contains("selectTaigaTreeGenerator"));
        Assert.assertFalse(text.contains("new BiomeMeta(EntityWolf.class, 2)"));
        Assert.assertFalse(text.contains("new WorldGenTaiga1()"));
        Assert.assertFalse(text.contains("new WorldGenTaiga2()"));
    }

    @Test
    public void biomeRainforestDelegatesTreeSelectionLogicToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(BIOME_RAINFOREST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeTreeGeneratorSelectionBehaviour"));
        Assert.assertTrue(text.contains("selectRainforestTreeGenerator"));
        Assert.assertFalse(text.contains("new WorldGenBigTree()"));
        Assert.assertFalse(text.contains("new WorldGenTrees()"));
    }

    @Test
    public void biomeBaseDelegatesClimateAndDefaultTreeSelectionLogicToCanonicalServices() throws IOException {
        String text = new String(Files.readAllBytes(BIOME_BASE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("BiomeClimateSelectionBehaviour"));
        Assert.assertTrue(text.contains("BiomeTreeGeneratorSelectionBehaviour"));
        Assert.assertTrue(text.contains("BiomeSpawnListLookupBehaviour"));
        Assert.assertTrue(text.contains("bootstrapClimateLookupTable"));
        Assert.assertTrue(text.contains("selectDefaultTreeGenerator"));
        Assert.assertTrue(text.contains("lookupBiome"));
        Assert.assertTrue(text.contains("selectClimateBiome"));
        Assert.assertTrue(text.contains("resolveSpawnList"));
        Assert.assertFalse(text.contains("random.nextInt(10) == 0 ? new WorldGenBigTree() : new WorldGenTrees()"));
        Assert.assertFalse(text.contains("for (int i = 0; i < 64; ++i)"));
        Assert.assertFalse(text.contains("f1 *= f;"));
        Assert.assertFalse(text.contains("enumcreaturetype == EnumCreatureType.MONSTER ? this.s"));
    }
}
