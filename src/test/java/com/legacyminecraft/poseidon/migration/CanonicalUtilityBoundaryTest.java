package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CanonicalUtilityBoundaryTest {
    private static final Path TNT_PRIMED_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/entity/TntPrimedBehaviour.java");
    private static final Path POSEIDON_VERSION_CHECKER_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/utility/PoseidonVersionChecker.java");

    @Test
    public void canonicalUtilityBehavioursUseBukkitServerApiInsteadOfCraftServerType() throws IOException {
        assertUsesServerApi(read(TNT_PRIMED_BEHAVIOUR_PATH), "Server server = tnt.world.getServer();");
        assertUsesServerApi(read(POSEIDON_VERSION_CHECKER_PATH), "private Server server;");
    }

    private static void assertUsesServerApi(String text, String signatureFragment) {
        Assert.assertTrue(text.contains("import org.bukkit.Server;"));
        Assert.assertTrue(text.contains(signatureFragment));
        Assert.assertFalse(text.contains("org.bukkit.craftbukkit.CraftServer"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }
}
