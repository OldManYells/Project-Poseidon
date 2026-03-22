package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalServerStartupFeedbackBoundaryTest {
    private static final Path SERVER_STARTUP_FEEDBACK_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ServerStartupFeedbackService.java");
    private static final Path SERVER_STARTUP_FEEDBACK_SYSTEM_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ServerStartupFeedbackSystem.java");

    @Test
    public void serverStartupFeedbackUsesBukkitServerApiInsteadOfCraftServerType() throws IOException {
        String serviceText = read(SERVER_STARTUP_FEEDBACK_SERVICE_PATH);
        String systemText = read(SERVER_STARTUP_FEEDBACK_SYSTEM_PATH);

        Assert.assertTrue(serviceText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(serviceText.contains("Server server,"));
        Assert.assertFalse(serviceText.contains("org.bukkit.craftbukkit.CraftServer"));

        Assert.assertTrue(systemText.contains("import org.bukkit.Server;"));
        Assert.assertTrue(systemText.contains("Server server,"));
        Assert.assertFalse(systemText.contains("org.bukkit.craftbukkit.CraftServer"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

