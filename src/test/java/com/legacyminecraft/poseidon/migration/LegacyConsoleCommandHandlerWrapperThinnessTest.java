package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyConsoleCommandHandlerWrapperThinnessTest {
    private static final Path CONSOLE_COMMAND_HANDLER_PATH = Paths.get("src/main/java/net/minecraft/server/ConsoleCommandHandler.java");

    @Test
    public void consoleCommandHandlerUsesRoleAlignedDelegateNaming() throws IOException {
        String text = new String(Files.readAllBytes(CONSOLE_COMMAND_HANDLER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ConsoleHelpMessageSystem"));
        Assert.assertTrue(text.contains("ConsoleCommandExecutionSystem"));
        Assert.assertTrue(text.contains("ConsoleCommandFeedbackSystem"));
        Assert.assertTrue(text.contains("ConsolePermissionPolicy"));
        Assert.assertTrue(text.contains("ConsoleWhitelistCommandSystem"));
        Assert.assertTrue(text.contains("consoleHelpMessageSystem"));
        Assert.assertTrue(text.contains("consoleCommandExecutionSystem"));
        Assert.assertTrue(text.contains("consoleCommandFeedbackSystem"));
        Assert.assertTrue(text.contains("consolePermissionPolicy"));
        Assert.assertTrue(text.contains("consoleWhitelistCommandSystem"));
        Assert.assertTrue(text.contains("commandSupport"));
        Assert.assertTrue(text.contains("whitelistPermissionGate"));
        Assert.assertTrue(text.contains("whitelistConsolePrinter"));
        Assert.assertFalse(text.contains("ConsoleHelpMessageService"));
        Assert.assertFalse(text.contains("ConsoleCommandExecutionService"));
        Assert.assertFalse(text.contains("ConsoleCommandFeedbackService"));
        Assert.assertFalse(text.contains("ConsolePermissionService"));
        Assert.assertFalse(text.contains("ConsoleWhitelistCommandService"));
        Assert.assertFalse(text.contains("consoleHelpMessageService"));
        Assert.assertFalse(text.contains("consoleCommandExecutionService"));
        Assert.assertFalse(text.contains("consoleCommandFeedbackService"));
        Assert.assertFalse(text.contains("consolePermissionService"));
        Assert.assertFalse(text.contains("consoleWhitelistCommandService"));
    }
}
