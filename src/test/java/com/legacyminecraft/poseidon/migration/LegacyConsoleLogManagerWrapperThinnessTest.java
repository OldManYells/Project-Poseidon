package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyConsoleLogManagerWrapperThinnessTest {
    private static final Path CONSOLE_LOG_MANAGER_PATH = Paths.get("src/main/java/net/minecraft/server/ConsoleLogManager.java");

    @Test
    public void consoleLogManagerUsesCanonicalConsoleLogFileConfigPolicy() throws IOException {
        String text = new String(Files.readAllBytes(CONSOLE_LOG_MANAGER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ConsoleLogFileConfigPolicy"));
        Assert.assertTrue(text.contains("CONSOLE_LOG_FILE_CONFIG_POLICY"));
        Assert.assertFalse(text.contains("\"settings.per-day-log-file.enabled\""));
        Assert.assertFalse(text.contains("\"settings.per-day-log-file.latest-log.enabled\""));
    }
}
