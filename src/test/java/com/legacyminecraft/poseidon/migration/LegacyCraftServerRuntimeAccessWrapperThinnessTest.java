package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerRuntimeAccessWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerRuntimeAccessBehaviour.java");

    @Test
    public void craftServerDelegatesRuntimeAccessorWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR_PATH);
        String maxPlayersSection = section(craftServerText, "public int getMaxPlayers() {", "public int getPort() {");
        String handleSection = section(craftServerText, "public ServerConfigurationManager getHandle() {", "// NOTE: Should only be called from MinecraftServer.b()");
        String runtimeSection = section(craftServerText, "public MinecraftServer getServer() {", "public World getWorld(String name) {");
        String logReaderSection = section(craftServerText, "public Logger getLogger() {", "public PluginCommand getPluginCommand(String name) {");
        String savePlayersSection = section(craftServerText, "public void savePlayers() {", "public void configureDbConfig(ServerConfig config) {");

        Assert.assertTrue(craftServerText.contains("CraftServerRuntimeAccessBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR.getMaxPlayers(server)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR.getHandle(server)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR.getServer(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR.getLogger(MinecraftServer.log)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR.getReader(console)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RUNTIME_ACCESS_BEHAVIOUR.savePlayers(server)"));

        Assert.assertFalse(maxPlayersSection.contains("return server.maxPlayers;"));
        Assert.assertFalse(handleSection.contains("return server;"));
        Assert.assertFalse(runtimeSection.contains("return console;"));
        Assert.assertFalse(logReaderSection.contains("return MinecraftServer.log;"));
        Assert.assertFalse(logReaderSection.contains("return console.reader;"));
        Assert.assertFalse(savePlayersSection.contains("server.savePlayers();"));

        Assert.assertTrue(behaviourText.contains("getMaxPlayers(ServerConfigurationManager configurationManager)"));
        Assert.assertTrue(behaviourText.contains("return configurationManager.maxPlayers;"));
        Assert.assertTrue(behaviourText.contains("getHandle(ServerConfigurationManager configurationManager)"));
        Assert.assertTrue(behaviourText.contains("return configurationManager;"));
        Assert.assertTrue(behaviourText.contains("getServer(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return console;"));
        Assert.assertTrue(behaviourText.contains("getLogger(Logger logger)"));
        Assert.assertTrue(behaviourText.contains("return logger;"));
        Assert.assertTrue(behaviourText.contains("getReader(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("return console.reader;"));
        Assert.assertTrue(behaviourText.contains("savePlayers(ServerConfigurationManager configurationManager)"));
        Assert.assertTrue(behaviourText.contains("configurationManager.savePlayers();"));
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
