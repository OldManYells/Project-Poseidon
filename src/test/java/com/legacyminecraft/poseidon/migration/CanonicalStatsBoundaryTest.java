package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalStatsBoundaryTest {
    private static final Path STAT_ARRAY_BUILDER_PATH = Paths.get("src/main/java/com/legacyminecraft/poseidon/world/stats/StatisticArrayBuilder.java");
    private static final Path CRAFTING_BOOTSTRAP_PATH = Paths.get("src/main/java/com/legacyminecraft/poseidon/world/stats/CraftingStatisticBootstrap.java");
    private static final Path STAT_BOOTSTRAP_PATH = Paths.get("src/main/java/com/legacyminecraft/poseidon/world/stats/StatisticBootstrap.java");

    @Test
    public void canonicalStatsServicesDoNotDependOnLegacyStatisticCollector() throws IOException {
        assertNoLegacyCollectorImport(STAT_ARRAY_BUILDER_PATH);
        assertNoLegacyCollectorImport(CRAFTING_BOOTSTRAP_PATH);
        assertNoLegacyCollectorImport(STAT_BOOTSTRAP_PATH);

        String statBootstrapText = new String(Files.readAllBytes(STAT_BOOTSTRAP_PATH), StandardCharsets.UTF_8);
        Assert.assertTrue(statBootstrapText.contains("CounterStatisticFactoryBehaviour"));
        Assert.assertFalse(statBootstrapText.contains("new CounterStatistic("));
    }

    private void assertNoLegacyCollectorImport(Path path) throws IOException {
        String text = new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
        Assert.assertFalse(path + " should not import legacy StatisticCollector", text.contains("import net.minecraft.server.StatisticCollector;"));
    }
}
