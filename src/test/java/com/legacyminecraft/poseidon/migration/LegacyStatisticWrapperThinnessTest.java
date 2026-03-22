package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyStatisticWrapperThinnessTest {
    private static final Path STATISTIC_STORAGE_PATH = Paths.get("src/main/java/net/minecraft/server/StatisticStorage.java");
    private static final Path STATISTIC_COLLECTOR_PATH = Paths.get("src/main/java/net/minecraft/server/StatisticCollector.java");
    private static final Path ACHIEVEMENT_PATH = Paths.get("src/main/java/net/minecraft/server/Achievement.java");
    private static final Path COUNTER_STATISTIC_PATH = Paths.get("src/main/java/net/minecraft/server/CounterStatistic.java");
    private static final Path ACHIEVEMENT_LIST_PATH = Paths.get("src/main/java/net/minecraft/server/AchievementList.java");
    private static final Path STATISTIC_LIST_PATH = Paths.get("src/main/java/net/minecraft/server/StatisticList.java");

    @Test
    public void statisticStorageDelegatesTranslationLoadingToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(STATISTIC_STORAGE_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("StatisticTranslationBehaviour"));
        Assert.assertFalse(text.contains("/lang/en_US.lang"));
        Assert.assertFalse(text.contains("/lang/stats_US.lang"));
        Assert.assertFalse(text.contains("Properties"));
    }

    @Test
    public void statisticCollectorDelegatesTranslationLookupsToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(STATISTIC_COLLECTOR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("StatisticTranslationBehaviour"));
        Assert.assertFalse(text.contains("StatisticStorage.a()"));
    }

    @Test
    public void achievementDelegatesIdAndTranslationDerivationToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(ACHIEVEMENT_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("AchievementDefinitionBehaviour"));
        Assert.assertFalse(text.contains("5242880 + i"));
        Assert.assertFalse(text.contains("\"achievement.\" + s"));
    }

    @Test
    public void counterStatisticDelegatesCounterListRegistrationToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(COUNTER_STATISTIC_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CounterStatisticRegistry"));
        Assert.assertFalse(text.contains("StatisticList.c.add(this)"));
    }

    @Test
    public void achievementListDelegatesDefaultAchievementBootstrapToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(ACHIEVEMENT_LIST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("AchievementBootstrap"));
        Assert.assertFalse(text.contains("new Achievement("));
    }

    @Test
    public void statisticListDelegatesCoreStatisticBootstrapToCanonicalService() throws IOException {
        String text = new String(Files.readAllBytes(STATISTIC_LIST_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("StatisticBootstrap"));
        Assert.assertFalse(text.contains("new CounterStatistic("));
    }
}
