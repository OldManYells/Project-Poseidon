package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyLowRiskWrapperNamingThinnessTest {
    private static final Path WORLD_LOADER_PATH = Paths.get("src/main/java/net/minecraft/server/WorldLoader.java");
    private static final Path WORLD_MAP_COLLECTION_PATH = Paths.get("src/main/java/net/minecraft/server/WorldMapCollection.java");
    private static final Path STATISTIC_STORAGE_PATH = Paths.get("src/main/java/net/minecraft/server/StatisticStorage.java");
    private static final Path PLAYER_NBT_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/PlayerNBTManager.java");
    private static final Path COUNTER_STATISTIC_PATH = Paths.get("src/main/java/net/minecraft/server/CounterStatistic.java");
    private static final Path NBT_TAG_STRING_PATH = Paths.get("src/main/java/net/minecraft/server/NBTTagString.java");
    private static final Path NBT_TAG_COMPOUND_PATH = Paths.get("src/main/java/net/minecraft/server/NBTTagCompound.java");

    @Test
    public void lowRiskWrappersUseRoleAlignedDelegateFieldNames() throws IOException {
        String worldLoaderText = read(WORLD_LOADER_PATH);
        String worldMapCollectionText = read(WORLD_MAP_COLLECTION_PATH);
        String statisticStorageText = read(STATISTIC_STORAGE_PATH);
        String playerNbtManagerText = read(PLAYER_NBT_MANAGER_PATH);
        String counterStatisticText = read(COUNTER_STATISTIC_PATH);
        String nbtTagStringText = read(NBT_TAG_STRING_PATH);
        String nbtTagCompoundText = read(NBT_TAG_COMPOUND_PATH);

        Assert.assertTrue(worldLoaderText.contains("worldLoaderSystem"));
        Assert.assertFalse(worldLoaderText.contains("worldLoaderService"));

        Assert.assertTrue(worldMapCollectionText.contains("worldMapCollectionSystem"));
        Assert.assertFalse(worldMapCollectionText.contains("worldMapCollectionService"));

        Assert.assertTrue(statisticStorageText.contains("statisticTranslationBehaviour"));
        Assert.assertFalse(statisticStorageText.contains("statisticTranslationService"));

        Assert.assertTrue(playerNbtManagerText.contains("playerNbtStorageSystem"));
        Assert.assertFalse(playerNbtManagerText.contains("playerNbtStorageService"));

        Assert.assertTrue(counterStatisticText.contains("counterStatisticRegistry"));
        Assert.assertFalse(counterStatisticText.contains("counterStatisticRegistryService"));

        Assert.assertTrue(nbtTagStringText.contains("nbtPrimitiveCodec"));
        Assert.assertFalse(nbtTagStringText.contains("nbtPrimitiveCodecService"));

        Assert.assertTrue(nbtTagCompoundText.contains("nbtCollectionCodec"));
        Assert.assertFalse(nbtTagCompoundText.contains("nbtCollectionCodecService"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
