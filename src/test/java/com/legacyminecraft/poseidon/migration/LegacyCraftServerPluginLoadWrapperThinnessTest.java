package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerPluginLoadWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_PLUGIN_LOAD_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPluginLoadBehaviour.java");

    @Test
    public void craftServerDelegatesPluginLoadingWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_PLUGIN_LOAD_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void loadPlugins() {", "public void enablePlugins(PluginLoadOrder type) {");

        Assert.assertTrue(craftServerText.contains("CraftServerPluginLoadBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PLUGIN_LOAD_BEHAVIOUR.loadPlugins(pluginManager, pluginFolder, Logger.getLogger(CraftServer.class.getName()));"));

        Assert.assertFalse(section.contains("pluginManager.registerInterface(JavaPluginLoader.class)"));
        Assert.assertFalse(section.contains("Plugin[] plugins = pluginManager.loadPlugins(pluginFolder);"));
        Assert.assertFalse(section.contains("plugin.onLoad();"));
        Assert.assertFalse(section.contains("pluginFolder.mkdir();"));

        Assert.assertTrue(behaviourText.contains("loadPlugins(PluginManager pluginManager, File pluginFolder, Logger logger)"));
        Assert.assertTrue(behaviourText.contains("pluginManager.registerInterface(JavaPluginLoader.class);"));
        Assert.assertTrue(behaviourText.contains("Plugin[] plugins = pluginManager.loadPlugins(pluginFolder);"));
        Assert.assertTrue(behaviourText.contains("plugin.onLoad();"));
        Assert.assertTrue(behaviourText.contains("pluginFolder.mkdir();"));
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
