package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyStatsWrapperThinnessTest {
    private static final Path CRAFTING_STATISTIC_PATH = Paths.get("src/main/java/net/minecraft/server/CraftingStatistic.java");
    private static final Path GUI_STATS_LISTENER_PATH = Paths.get("src/main/java/net/minecraft/server/GuiStatsListener.java");
    private static final Path UNKNOWN_COUNTER_PATH = Paths.get("src/main/java/net/minecraft/server/UnknownCounter.java");
    private static final Path TIME_COUNTER_PATH = Paths.get("src/main/java/net/minecraft/server/TimeCounter.java");
    private static final Path DISTANCES_COUNTER_PATH = Paths.get("src/main/java/net/minecraft/server/DistancesCounter.java");

    @Test
    public void craftingStatisticDelegatesCraftedItemIdHandlingToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFTING_STATISTIC_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("CraftingStatisticBehaviour"));
        Assert.assertTrue(text.contains("CRAFTING_STATISTIC_BEHAVIOUR.resolveCraftedItemId"));
        Assert.assertFalse(text.contains("this.a = j;"));
    }

    @Test
    public void guiStatsListenerDelegatesRefreshActionToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(GUI_STATS_LISTENER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("GuiStatsRefreshActionBehaviour"));
        Assert.assertTrue(text.contains("GUI_STATS_REFRESH_ACTION_BEHAVIOUR.bindComponent"));
        Assert.assertTrue(text.contains("GUI_STATS_REFRESH_ACTION_BEHAVIOUR.onRefreshAction"));
        Assert.assertFalse(text.contains("GuiStatsComponent.a(this.a);"));
    }

    @Test
    public void counterVariantsDelegateInitializationHooksToCanonicalBehaviour() throws IOException {
        String unknownText = new String(Files.readAllBytes(UNKNOWN_COUNTER_PATH), StandardCharsets.UTF_8);
        String timeText = new String(Files.readAllBytes(TIME_COUNTER_PATH), StandardCharsets.UTF_8);
        String distancesText = new String(Files.readAllBytes(DISTANCES_COUNTER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(unknownText.contains("CounterVariantBehaviour"));
        Assert.assertTrue(unknownText.contains("COUNTER_VARIANT_BEHAVIOUR.initializeUnknownCounter()"));
        Assert.assertFalse(unknownText.contains("UnknownCounter() {}"));

        Assert.assertTrue(timeText.contains("CounterVariantBehaviour"));
        Assert.assertTrue(timeText.contains("COUNTER_VARIANT_BEHAVIOUR.initializeTimeCounter()"));
        Assert.assertFalse(timeText.contains("TimeCounter() {}"));

        Assert.assertTrue(distancesText.contains("CounterVariantBehaviour"));
        Assert.assertTrue(distancesText.contains("COUNTER_VARIANT_BEHAVIOUR.initializeDistancesCounter()"));
        Assert.assertFalse(distancesText.contains("DistancesCounter() {}"));
    }
}
