package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalPoseidonCoreBoundaryTest {
    private static final Path POSEIDON_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/Poseidon.java");
    private static final Path POSEIDON_STATISTICS_AGENT_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/PoseidonStatisticsAgent.java");

    @Test
    public void canonicalPoseidonCoreUsesDiagnosticsBridgeInsteadOfCraftServerType() throws IOException {
        String poseidonText = read(POSEIDON_PATH);
        String poseidonStatisticsAgentText = read(POSEIDON_STATISTICS_AGENT_PATH);

        Assert.assertFalse(poseidonText.contains("import org.bukkit.craftbukkit.CraftServer;"));
        Assert.assertTrue(poseidonText.contains("ServerDiagnosticsBridgeBehaviour"));
        Assert.assertTrue(poseidonText.contains("serverDiagnosticsBridge.readTpsRecords"));

        Assert.assertFalse(poseidonStatisticsAgentText.contains("import org.bukkit.craftbukkit.CraftServer;"));
        Assert.assertTrue(poseidonStatisticsAgentText.contains("ServerDiagnosticsBridgeBehaviour"));
        Assert.assertTrue(poseidonStatisticsAgentText.contains("readPoseidonVersion"));
        Assert.assertTrue(poseidonStatisticsAgentText.contains("readPoseidonReleaseType"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
