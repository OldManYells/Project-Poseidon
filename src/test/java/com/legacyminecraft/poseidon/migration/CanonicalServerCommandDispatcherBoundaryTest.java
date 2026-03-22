package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalServerCommandDispatcherBoundaryTest {
    private static final Path SERVER_COMMAND_DISPATCHER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/ServerCommandDispatcher.java");

    @Test
    public void serverCommandDispatcherUsesBukkitApiAbstractionsInsteadOfCraftBukkitTypes() throws IOException {
        String text = new String(Files.readAllBytes(SERVER_COMMAND_DISPATCHER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("ServerCommandEnvelopeBehaviour"));
        Assert.assertTrue(text.contains("import org.bukkit.Server;"));
        Assert.assertTrue(text.contains("import org.bukkit.command.ConsoleCommandSender;"));
        Assert.assertTrue(text.contains("drainQueuedCommands("));
        Assert.assertTrue(text.contains("List<ServerCommandEnvelopeBehaviour.ServerCommandState> pendingCommands"));
        Assert.assertTrue(text.contains("ConsoleCommandSender consoleSender"));
        Assert.assertTrue(text.contains("Server server"));
        Assert.assertFalse(text.contains("import net.minecraft.server.ServerCommand;"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.CraftServer"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.command.ColouredConsoleSender"));
    }
}
