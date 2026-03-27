package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerPluginActivationWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_PLUGIN_ACTIVATION_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPluginActivationBehaviour.java");

    @Test
    public void craftServerDelegatesPluginActivationWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_PLUGIN_ACTIVATION_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void enablePlugins(PluginLoadOrder type) {", "public void disablePlugins() {");

        Assert.assertTrue(craftServerText.contains("CraftServerPluginActivationBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PLUGIN_ACTIVATION_BEHAVIOUR.enablePlugins("));
        Assert.assertTrue(craftServerText.contains("new CraftServerPluginActivationBehaviour.PluginLoadDispatcher()"));
        Assert.assertTrue(craftServerText.contains("new CraftServerPluginActivationBehaviour.PostWorldBootstrapAction()"));

        Assert.assertFalse(section.contains("if ((!plugin.isEnabled()) && (plugin.getDescription().getLoad() == type))"));
        Assert.assertFalse(section.contains("if (type == PluginLoadOrder.POSTWORLD)"));

        Assert.assertTrue(behaviourText.contains("enablePlugins(Plugin[] plugins, PluginLoadOrder type, PluginLoadDispatcher dispatcher, PostWorldBootstrapAction postWorldBootstrapAction)"));
        Assert.assertTrue(behaviourText.contains("if ((!plugin.isEnabled()) && (plugin.getDescription().getLoad() == type))"));
        Assert.assertTrue(behaviourText.contains("dispatcher.loadPlugin(plugin);"));
        Assert.assertTrue(behaviourText.contains("if (type == PluginLoadOrder.POSTWORLD) {"));
        Assert.assertTrue(behaviourText.contains("postWorldBootstrapAction.run();"));
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
