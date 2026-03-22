package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftBukkitServerCommandListenerThinnessTest {
    private static final Path SERVER_COMMAND_LISTENER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/command/ServerCommandListener.java");
    private static final Path CONSOLE_PERMISSION_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ConsolePermissionService.java");
    private static final Path CONSOLE_COMMAND_FEEDBACK_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ConsoleCommandFeedbackService.java");

    @Test
    public void craftBukkitCommandListenerDelegatesToCanonicalBehaviourAndBridge() throws IOException {
        String listenerText = read(SERVER_COMMAND_LISTENER_PATH);
        String permissionServiceText = read(CONSOLE_PERMISSION_SERVICE_PATH);
        String feedbackServiceText = read(CONSOLE_COMMAND_FEEDBACK_SERVICE_PATH);

        Assert.assertTrue(listenerText.contains("implements ICommandListener, CommandSenderBackedListener"));
        Assert.assertTrue(listenerText.contains("ServerCommandListenerBehaviour"));
        Assert.assertTrue(listenerText.contains("serverCommandListenerBehaviour.resolvePrefix("));
        Assert.assertTrue(listenerText.contains("serverCommandListenerBehaviour.resolveNameOrPrefix("));
        Assert.assertFalse(listenerText.contains("getClass().getMethod(\"getName\")"));
        Assert.assertFalse(listenerText.contains("String[] parts = commandSender.getClass().getName().split(\"\\\\.\")"));

        Assert.assertTrue(permissionServiceText.contains("CommandSenderBackedListener"));
        Assert.assertFalse(permissionServiceText.contains("org.bukkit.craftbukkit.command.ServerCommandListener"));
        Assert.assertTrue(feedbackServiceText.contains("CommandSenderBackedListener"));
        Assert.assertFalse(feedbackServiceText.contains("org.bukkit.craftbukkit.command.ServerCommandListener"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}

