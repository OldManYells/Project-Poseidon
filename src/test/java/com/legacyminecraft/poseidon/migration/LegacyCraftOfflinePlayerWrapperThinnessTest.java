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

        Assert.assertTrue(text.contains("OfflinePlayerIdentityBehaviour"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_IDENTITY_BEHAVIOUR"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_IDENTITY_BEHAVIOUR.isOnline()"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_IDENTITY_BEHAVIOUR.getName(name)"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_IDENTITY_BEHAVIOUR.getServer(server)"));
        Assert.assertTrue(text.contains("OfflinePlayerModerationBehaviour"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_MODERATION_BEHAVIOUR"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_MODERATION_BEHAVIOUR.isOp(server, name, getName())"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_MODERATION_BEHAVIOUR.setOp(server, name, getName(), value)"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_MODERATION_BEHAVIOUR.isBanned(server, name, getName())"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_MODERATION_BEHAVIOUR.setBanned(server, name, getName(), value)"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_MODERATION_BEHAVIOUR.isWhitelisted(server, name, getName())"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_MODERATION_BEHAVIOUR.setWhitelisted(server, name, getName(), value)"));
        Assert.assertTrue(text.contains("OfflinePlayerSnapshotCaptureBehaviour"));
        Assert.assertTrue(text.contains("OFFLINE_PLAYER_SNAPSHOT_CAPTURE_BEHAVIOUR"));
        Assert.assertTrue(text.contains("capture(server, name)"));
        Assert.assertFalse(text.contains("OfflinePlayerAccessReadPolicy"));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_ACCESS_READ_POLICY"));
        Assert.assertFalse(text.contains("OfflinePlayerNameResolutionBehaviour"));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_NAME_RESOLUTION_BEHAVIOUR"));
        Assert.assertFalse(text.contains("OfflinePlayerAccessToggleBehaviour"));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_ACCESS_TOGGLE_BEHAVIOUR"));
        Assert.assertFalse(text.contains("OfflinePlayerOperatorToggleBehaviour"));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_OPERATOR_TOGGLE_BEHAVIOUR"));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_ACCESS_READ_POLICY.isOperator("));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_OPERATOR_TOGGLE_BEHAVIOUR.setOp("));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_ACCESS_READ_POLICY.isBanned("));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_ACCESS_TOGGLE_BEHAVIOUR.setBanned("));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_ACCESS_READ_POLICY.isWhitelisted("));
        Assert.assertFalse(text.contains("OFFLINE_PLAYER_ACCESS_TOGGLE_BEHAVIOUR.setWhitelisted("));
        Assert.assertFalse(text.contains("server.getHandle().isOp(getName().toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().banByName.contains(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().e().contains(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().e(getName().toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().f(getName().toLowerCase())"));
        Assert.assertFalse(text.contains("if (value == isOp()) return;"));
        Assert.assertFalse(text.contains("return false;"));
        Assert.assertFalse(text.contains("return name;"));
        Assert.assertFalse(text.contains("return server;"));
        Assert.assertFalse(text.contains("isOperator(server, getName())"));
        Assert.assertFalse(text.contains("setOp(server, getName(), value)"));
        Assert.assertFalse(text.contains("isBanned(server, name)"));
        Assert.assertFalse(text.contains("setBanned(server, name, value)"));
        Assert.assertFalse(text.contains("isWhitelisted(server, name)"));
        Assert.assertFalse(text.contains("setWhitelisted(server, name, value)"));
        Assert.assertFalse(text.contains("this.server = server;"));
        Assert.assertFalse(text.contains("this.name = name;"));
        Assert.assertFalse(text.contains("server.getHandle().a(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().b(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().k(name.toLowerCase())"));
        Assert.assertFalse(text.contains("server.getHandle().l(name.toLowerCase())"));
    }
}
