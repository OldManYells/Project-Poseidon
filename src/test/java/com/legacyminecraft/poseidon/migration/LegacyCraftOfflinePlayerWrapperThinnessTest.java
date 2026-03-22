package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftOfflinePlayerWrapperThinnessTest {
    private static final Path CRAFT_OFFLINE_PLAYER_PATH =
            Paths.get("src/main/java/org/bukkit/craftbukkit/CraftOfflinePlayer.java");

    @Test
    public void craftOfflinePlayerDelegatesAccessListAndOperatorRulesToCanonicalBehaviour() throws IOException {
        String text = new String(Files.readAllBytes(CRAFT_OFFLINE_PLAYER_PATH), StandardCharsets.UTF_8);

        Assert.assertTrue(text.contains("OfflinePlayerAccessBehaviour"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_ACCESS_BEHAVIOUR"));
        Assert.assertTrue(text.contains("isOperator(server, getName())"));
        Assert.assertTrue(text.contains("setOperator(server, getName(), value)"));
        Assert.assertTrue(text.contains("isBanned(server, name)"));
        Assert.assertTrue(text.contains("setBanned(server, name, value)"));
        Assert.assertTrue(text.contains("isWhitelisted(server, name)"));
        Assert.assertTrue(text.contains("setWhitelisted(server, name, value)"));
        Assert.assertFalse(text.contains("server.getHandle().isOp(getName().toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().banByName.contains(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().e().contains(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().e(getName().toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().f(getName().toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().a(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().b(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().k(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().l(name.toLowerCase())"));
    }
}

