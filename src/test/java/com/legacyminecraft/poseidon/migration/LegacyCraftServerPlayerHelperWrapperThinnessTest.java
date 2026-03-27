package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerPlayerHelperWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_PLAYER_HELPER_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPlayerHelperBehaviour.java");

    @Test
    public void craftServerDelegatesPlayerHelperWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_PLAYER_HELPER_BEHAVIOUR_PATH);
        String playerSection = section(craftServerText, "public Player getPlayer(final EntityPlayer entity) {", "public int getMaxPlayers() {");
        String offlineSection = section(craftServerText, "public OfflinePlayer getOfflinePlayer(String name) {", "public Set<String> getIPBans() {");

        Assert.assertTrue(craftServerText.contains("CraftServerPlayerHelperBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PLAYER_HELPER_BEHAVIOUR.getPlayer(entity)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PLAYER_HELPER_BEHAVIOUR.matchPlayer(getOnlinePlayers(), partialName)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PLAYER_HELPER_BEHAVIOUR.getOfflinePlayer("));
        Assert.assertTrue(craftServerText.contains("new CraftServerPlayerHelperBehaviour.OfflinePlayerFactory()"));
        Assert.assertTrue(craftServerText.contains("new CraftOfflinePlayer(CraftServer.this, offlinePlayerName)"));

        Assert.assertFalse(playerSection.contains("return entity.netServerHandler.getPlayer();"));
        Assert.assertFalse(playerSection.contains("for (Player iterPlayer : this.getOnlinePlayers())"));
        Assert.assertFalse(playerSection.contains("iterPlayerName.toLowerCase().indexOf(partialName.toLowerCase())"));
        Assert.assertFalse(offlineSection.contains("new CraftOfflinePlayer(this, name)"));

        Assert.assertTrue(behaviourText.contains("getPlayer(EntityPlayer entityPlayer)"));
        Assert.assertTrue(behaviourText.contains("return entityPlayer.netServerHandler.getPlayer();"));
        Assert.assertTrue(behaviourText.contains("matchPlayer(Player[] onlinePlayers, String partialName)"));
        Assert.assertTrue(behaviourText.contains("iterPlayerName.toLowerCase().indexOf(partialName.toLowerCase())"));
        Assert.assertTrue(behaviourText.contains("getOfflinePlayer("));
        Assert.assertTrue(behaviourText.contains("OfflinePlayer exactPlayer"));
        Assert.assertTrue(behaviourText.contains("OfflinePlayerFactory offlinePlayerFactory"));
        Assert.assertTrue(behaviourText.contains("return offlinePlayerFactory.create(name);"));
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
