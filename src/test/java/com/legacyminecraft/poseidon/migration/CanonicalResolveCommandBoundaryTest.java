package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalResolveCommandBoundaryTest {
    private static final Path RESOLVE_COMMAND_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/commands/ResolveCommand.java");

    @Test
    public void resolveCommandNoLongerImportsCraftServerType() throws IOException {
        String text = new String(Files.readAllBytes(RESOLVE_COMMAND_PATH), StandardCharsets.UTF_8);

        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit.CraftServer;"));
        Assert.assertTrue(text.contains("Object bukkitServer = Bukkit.getServer();"));
        Assert.assertTrue(text.contains("bukkitServer.getClass().getDeclaredField(\"commandMap\")"));
    }
}
