package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalPoseidonServerBoundaryTest {
    private static final Path POSEIDON_SERVER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/PoseidonServer.java");

    @Test
    public void poseidonServerUsesBukkitServerApiAndReflectiveLegacyRegistration() throws IOException {
        String text = new String(Files.readAllBytes(POSEIDON_SERVER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("import org.bukkit.Server;"));
        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit.CraftServer;"));
        Assert.assertTrue(text.contains("kernel.registerService(Server.class, bukkitServer);"));
        Assert.assertTrue(text.contains("Class.forName(\"org.bukkit.craftbukkit.CraftServer\")"));
    }
}
