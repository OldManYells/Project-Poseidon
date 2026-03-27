package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerBanWhitelistWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerBanWhitelistBehaviour.java");

    @Test
    public void craftServerDelegatesBanAndWhitelistWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String banWhitelistBehaviourText = read(CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR_PATH);
        String banWhitelistSection = section(craftServerText,
                "public Set<String> getIPBans() {",
                "public boolean isShuttingdown() {");

        Assert.assertTrue(craftServerText.contains("CraftServerBanWhitelistBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR.getIPBans(server)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR.banIP(server, address)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR.unbanIP(server, address)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR.getBannedPlayers(this)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR.setWhitelist(console, server, value)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR.getWhitelistedPlayers(this, server)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_BAN_WHITELIST_BEHAVIOUR.reloadWhitelist(server)"));

        Assert.assertFalse(banWhitelistSection.contains("return new HashSet(server.banByIP);"));
        Assert.assertFalse(banWhitelistSection.contains("server.c(address);"));
        Assert.assertFalse(banWhitelistSection.contains("server.d(address);"));
        Assert.assertFalse(banWhitelistSection.contains("for (Object name : server.banByName)"));
        Assert.assertFalse(banWhitelistSection.contains("server.o = value;"));
        Assert.assertFalse(banWhitelistSection.contains("console.propertyManager.b(\"white-list\", value);"));
        Assert.assertFalse(banWhitelistSection.contains("console.propertyManager.savePropertiesFile();"));
        Assert.assertFalse(banWhitelistSection.contains("for (Object name : server.e())"));
        Assert.assertFalse(banWhitelistSection.contains("server.f();"));

        Assert.assertTrue(banWhitelistBehaviourText.contains("getIPBans(ServerConfigurationManager configurationManager)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("return new HashSet<String>(configurationManager.banByIP);"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("banIP(ServerConfigurationManager configurationManager, String address)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("configurationManager.c(address);"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("unbanIP(ServerConfigurationManager configurationManager, String address)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("configurationManager.d(address);"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("getBannedPlayers(CraftServer server)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("for (Object playerName : server.getHandle().banByName)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("server.getOfflinePlayer((String) playerName)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("setWhitelist(MinecraftServer console, ServerConfigurationManager configurationManager, boolean value)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("configurationManager.o = value;"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("console.propertyManager.b(\"white-list\", value);"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("console.propertyManager.savePropertiesFile();"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("getWhitelistedPlayers(CraftServer server, ServerConfigurationManager configurationManager)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("for (Object playerName : configurationManager.e())"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("reloadWhitelist(ServerConfigurationManager configurationManager)"));
        Assert.assertTrue(banWhitelistBehaviourText.contains("configurationManager.f();"));
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
