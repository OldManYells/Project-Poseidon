package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalCraftBukkitBootstrapBoundaryTest {
    private static final Path CRAFT_BUKKIT_BOOTSTRAP_SERVICE_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/runtime/CraftBukkitBootstrapService.java");

    @Test
    public void craftBukkitBootstrapServiceNoLongerImportsCraftServerType() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_BUKKIT_BOOTSTRAP_SERVICE_PATH), StandardCharsets.UTF_8);

        Assert.assertFalse(text.contains("import org.bukkit.craftbukkit.CraftServer;"));
        Assert.assertTrue(text.contains("resolveImplementationVersion()"));
    }
}
