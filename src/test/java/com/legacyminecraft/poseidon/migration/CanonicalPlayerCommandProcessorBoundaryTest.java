package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalPlayerCommandProcessorBoundaryTest {
    private static final Path PLAYER_COMMAND_PROCESSOR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/PlayerCommandProcessor.java");

    @Test
    public void playerCommandProcessorUsesBukkitApiAbstractionsInsteadOfCraftTypes() throws IOException {
        String text = new String(Files.readAllBytes(PLAYER_COMMAND_PROCESSOR_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import org.bukkit.Server;"));
        Assert.assertTrue(text.contains("import org.bukkit.entity.Player;"));
        Assert.assertTrue(text.contains("handlePlayerCommand(Server server, Player player, String command, Logger logger)"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.CraftServer"));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.entity.CraftPlayer"));
    }
}

