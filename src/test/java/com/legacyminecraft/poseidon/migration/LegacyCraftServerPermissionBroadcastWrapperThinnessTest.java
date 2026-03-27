package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerPermissionBroadcastWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_PERMISSION_BROADCAST_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPermissionBroadcastBehaviour.java");

    @Test
    public void craftServerDelegatesPermissionBroadcastWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_PERMISSION_BROADCAST_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public int broadcast(String message, String permission) {", "public OfflinePlayer getOfflinePlayer(String name) {");

        Assert.assertTrue(craftServerText.contains("CraftServerPermissionBroadcastBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PERMISSION_BROADCAST_BEHAVIOUR.broadcast(getOnlinePlayers(), message)"));
        Assert.assertFalse(section.contains("Player[] players = getOnlinePlayers();"));
        Assert.assertFalse(section.contains("player.sendMessage(message);"));
        Assert.assertFalse(section.contains("return players.length;"));

        Assert.assertTrue(behaviourText.contains("broadcast(Player[] players, String message)"));
        Assert.assertTrue(behaviourText.contains("player.sendMessage(message);"));
        Assert.assertTrue(behaviourText.contains("return players.length;"));
    }

    private static String read(Path path) throws IOException {
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

    private static String section(String text, String startMarker, String endMarker) {
        int startIndex = text.indexOf(startMarker);
        int endIndex = text.indexOf(endMarker);
        Assert.assertTrue(startIndex >= 0);
        Assert.assertTrue(endIndex >= 0);
        Assert.assertTrue(endIndex > startIndex);
        return text.substring(startIndex, endIndex);
    }
}
