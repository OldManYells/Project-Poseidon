package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_METADATA_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerMetadataBehaviour.java");
    private static final Path CRAFT_SERVER_PLAYER_LOOKUP_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPlayerLookupBehaviour.java");

    @Test
    public void craftServerDelegatesMetadataAndPlayerLookupWrappersToCanonicalBehaviours() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String metadataBehaviourText = read(CRAFT_SERVER_METADATA_BEHAVIOUR_PATH);
        String playerLookupBehaviourText = read(CRAFT_SERVER_PLAYER_LOOKUP_BEHAVIOUR_PATH);
        String onlinePlayersSection = section(craftServerText,
                "public Player[] getOnlinePlayers() {",
                "public Player getPlayer(final String name) {");
        String playerByNameSection = section(craftServerText,
                "public Player getPlayer(final String name) {",
                "public Player getPlayer(final UUID uuid) {");
        String playerByUuidSection = section(craftServerText,
                "public Player getPlayer(final UUID uuid) {",
                "public Player getPlayerExact(String name) {");
        String playerExactSection = section(craftServerText,
                "public Player getPlayerExact(String name) {",
                "public int broadcastMessage(String message) {");

        Assert.assertTrue(craftServerText.contains("CraftServerMetadataBehaviour"));
        Assert.assertTrue(craftServerText.contains("CraftServerPlayerLookupBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_METADATA_BEHAVIOUR.getGameVersion(GameVersion)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_METADATA_BEHAVIOUR.getName(serverName)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_METADATA_BEHAVIOUR.getPoseidonVersion(serverVersion)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_METADATA_BEHAVIOUR.getPoseidonReleaseType(releaseType)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_METADATA_BEHAVIOUR.getServerEnvironment(serverEnvironment)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_METADATA_BEHAVIOUR.getVersion(serverVersion, protocolVersion)"));
        Assert.assertTrue(onlinePlayersSection.contains("CRAFT_SERVER_PLAYER_LOOKUP_BEHAVIOUR.getOnlinePlayers(server.players)"));
        Assert.assertTrue(playerByNameSection.contains("CRAFT_SERVER_PLAYER_LOOKUP_BEHAVIOUR.getPlayer(getOnlinePlayers(), name)"));
        Assert.assertTrue(playerByUuidSection.contains("CRAFT_SERVER_PLAYER_LOOKUP_BEHAVIOUR.getPlayer(getOnlinePlayers(), uuid)"));
        Assert.assertTrue(playerExactSection.contains("CRAFT_SERVER_PLAYER_LOOKUP_BEHAVIOUR.getPlayerExact(getOnlinePlayers(), name)"));
        Assert.assertFalse(craftServerText.contains("return getGameVersion();"));
        Assert.assertFalse(craftServerText.contains("return serverName;"));
        Assert.assertFalse(craftServerText.contains("return serverVersion;"));
        Assert.assertFalse(craftServerText.contains("return releaseType;"));
        Assert.assertFalse(craftServerText.contains("return serverEnvironment;"));
        Assert.assertFalse(craftServerText.contains("return serverVersion + \" (MC: \" + protocolVersion + \")\";"));
        Assert.assertFalse(onlinePlayersSection.contains("Bukkit.getOnlinePlayers()"));
        Assert.assertFalse(onlinePlayersSection.contains("Player[] players = new Player[online.size()];"));
        Assert.assertFalse(playerByNameSection.contains("Player found = null;"));
        Assert.assertFalse(playerByNameSection.contains("String lowerName = name.toLowerCase();"));
        Assert.assertFalse(playerByNameSection.contains("player.getName().toLowerCase().startsWith(lowerName)"));
        Assert.assertFalse(playerByUuidSection.contains("for (Player p : Bukkit.getOnlinePlayers())"));
        Assert.assertFalse(playerByUuidSection.contains("p.getUniqueId().equals(uuid)"));
        Assert.assertFalse(playerExactSection.contains("String lname = name.toLowerCase();"));
        Assert.assertFalse(playerExactSection.contains("player.getName().equalsIgnoreCase(lname)"));

        Assert.assertTrue(metadataBehaviourText.contains("getGameVersion(String gameVersion)"));
        Assert.assertTrue(metadataBehaviourText.contains("getName(String serverName)"));
        Assert.assertTrue(metadataBehaviourText.contains("getPoseidonVersion(String serverVersion)"));
        Assert.assertTrue(metadataBehaviourText.contains("getPoseidonReleaseType(String releaseType)"));
        Assert.assertTrue(metadataBehaviourText.contains("getServerEnvironment(String serverEnvironment)"));
        Assert.assertTrue(metadataBehaviourText.contains("getVersion(String serverVersion, String protocolVersion)"));
        Assert.assertTrue(metadataBehaviourText.contains("return gameVersion;"));
        Assert.assertTrue(metadataBehaviourText.contains("return serverName;"));
        Assert.assertTrue(metadataBehaviourText.contains("return serverVersion;"));
        Assert.assertTrue(metadataBehaviourText.contains("return releaseType;"));
        Assert.assertTrue(metadataBehaviourText.contains("return serverEnvironment;"));
        Assert.assertTrue(metadataBehaviourText.contains("return serverVersion + \" (MC: \" + protocolVersion + \")\";"));

        Assert.assertTrue(playerLookupBehaviourText.contains("getOnlinePlayers(List players)"));
        Assert.assertTrue(playerLookupBehaviourText.contains("NmsEntityProjectionBridgeBehaviour"));
        Assert.assertTrue(playerLookupBehaviourText.contains("NMS_ENTITY_PROJECTION_BRIDGE_BEHAVIOUR.resolveOnlinePlayer(players.get(playerIndex))"));
        Assert.assertTrue(playerLookupBehaviourText.contains("bestDelta"));
        Assert.assertTrue(playerLookupBehaviourText.contains("normalizedName"));
        Assert.assertTrue(playerLookupBehaviourText.contains("playerName.startsWith(normalizedName)"));
        Assert.assertTrue(playerLookupBehaviourText.contains("player.getUniqueId().equals(uuid)"));
        Assert.assertTrue(playerLookupBehaviourText.contains("player.getName().equalsIgnoreCase(normalizedName)"));
        Assert.assertFalse(playerLookupBehaviourText.contains("((EntityPlayer) players.get(playerIndex)).netServerHandler.getPlayer()"));
        Assert.assertFalse(playerLookupBehaviourText.contains("Bukkit.getOnlinePlayers()"));
        Assert.assertFalse(playerLookupBehaviourText.contains("server.players"));
        Assert.assertFalse(playerLookupBehaviourText.contains("Player found = null;"));
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
