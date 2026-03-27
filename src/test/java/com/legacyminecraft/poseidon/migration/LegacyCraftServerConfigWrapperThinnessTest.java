package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerConfigWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_CONFIG_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerConfigBehaviour.java");

    @Test
    public void craftServerDelegatesConfigAndPropertyAccessWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_CONFIG_BEHAVIOUR_PATH);
        String configSection = section(craftServerText,
                "public int getPort() {",
                "public String getUpdateFolder() {");
        String serverStateSection = section(craftServerText,
                "public boolean getOnlineMode() {",
                "public ChunkGenerator getGenerator(String world) {");

        Assert.assertTrue(craftServerText.contains("CraftServerConfigBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getPort(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getViewDistance(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getIp(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getServerName(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getServerId(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getAllowNether(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.hasWhitelist(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getOnlineMode(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_BEHAVIOUR.getAllowFlight(console)"));

        Assert.assertFalse(configSection.contains("this.getConfigInt(\"server-port\", 25565)"));
        Assert.assertFalse(configSection.contains("this.getConfigInt(\"view-distance\", 10)"));
        Assert.assertFalse(configSection.contains("this.getConfigString(\"server-ip\", \"\")"));
        Assert.assertFalse(configSection.contains("this.getConfigString(\"server-name\", \"Unknown Server\")"));
        Assert.assertFalse(configSection.contains("this.getConfigString(\"server-id\", \"unnamed\")"));
        Assert.assertFalse(configSection.contains("this.getConfigBoolean(\"allow-nether\", true)"));
        Assert.assertFalse(configSection.contains("this.getConfigBoolean(\"white-list\", false)"));
        Assert.assertFalse(configSection.contains("console.propertyManager.getString"));
        Assert.assertFalse(configSection.contains("console.propertyManager.getInt"));
        Assert.assertFalse(configSection.contains("console.propertyManager.getBoolean"));
        Assert.assertFalse(configSection.contains("private String getConfigString"));
        Assert.assertFalse(configSection.contains("private int getConfigInt"));
        Assert.assertFalse(configSection.contains("private boolean getConfigBoolean"));
        Assert.assertFalse(serverStateSection.contains("return this.console.onlineMode;"));
        Assert.assertFalse(serverStateSection.contains("return this.console.allowFlight;"));

        Assert.assertTrue(behaviourText.contains("CraftServerConfigBehaviour"));
        Assert.assertTrue(behaviourText.contains("getPort(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfigInt(console, \"server-port\", 25565);"));
        Assert.assertTrue(behaviourText.contains("getViewDistance(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfigInt(console, \"view-distance\", 10);"));
        Assert.assertTrue(behaviourText.contains("getIp(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfigString(console, \"server-ip\", \"\");"));
        Assert.assertTrue(behaviourText.contains("getServerName(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfigString(console, \"server-name\", \"Unknown Server\");"));
        Assert.assertTrue(behaviourText.contains("getServerId(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfigString(console, \"server-id\", \"unnamed\");"));
        Assert.assertTrue(behaviourText.contains("getAllowNether(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfigBoolean(console, \"allow-nether\", true);"));
        Assert.assertTrue(behaviourText.contains("hasWhitelist(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfigBoolean(console, \"white-list\", false);"));
        Assert.assertTrue(behaviourText.contains("getOnlineMode(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return console.onlineMode;"));
        Assert.assertTrue(behaviourText.contains("getAllowFlight(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return console.allowFlight;"));
        Assert.assertTrue(behaviourText.contains("private String getConfigString(MinecraftServer console, String variable, String defaultValue)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfig(console).getString(variable, defaultValue);"));
        Assert.assertTrue(behaviourText.contains("private int getConfigInt(MinecraftServer console, String variable, int defaultValue)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfig(console).getInt(variable, defaultValue);"));
        Assert.assertTrue(behaviourText.contains("private boolean getConfigBoolean(MinecraftServer console, String variable, boolean defaultValue)"));
        Assert.assertTrue(behaviourText.contains("return this.getConfig(console).getBoolean(variable, defaultValue);"));
        Assert.assertTrue(behaviourText.contains("private PropertyManager getConfig(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return console.propertyManager;"));
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
