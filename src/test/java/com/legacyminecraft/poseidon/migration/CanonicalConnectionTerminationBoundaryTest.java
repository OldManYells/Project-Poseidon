package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalConnectionTerminationBoundaryTest {
    private static final Path CONNECTION_KICK_PROCESSOR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/ConnectionKickProcessor.java");
    private static final Path CONNECTION_TERMINATION_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/network/ConnectionTerminationSystem.java");

    @Test
    public void connectionKickAndTerminationUseBukkitServerApiInsteadOfCraftServerType() throws IOException {
        String kickProcessorText = read(CONNECTION_KICK_PROCESSOR_PATH);
        String terminationSystemText = read(CONNECTION_TERMINATION_SYSTEM_PATH);

        Assert.assertTrue(kickProcessorText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(kickProcessorText.contains("processKick(Server server"));
        Assert.assertFalse(kickProcessorText.contains("org.bukkit.craftbukkit.CraftServer"));

        Assert.assertTrue(terminationSystemText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(terminationSystemText.contains("Server server,"));
        Assert.assertFalse(terminationSystemText.contains("org.bukkit.craftbukkit.CraftServer"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

