package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerPluginDisableWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_PLUGIN_DISABLE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPluginDisableBehaviour.java");

    @Test
    public void craftServerDelegatesPluginDisableWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_PLUGIN_DISABLE_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void disablePlugins() {", "private void loadPlugin(Plugin plugin) {");

        Assert.assertTrue(craftServerText.contains("CraftServerPluginDisableBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PLUGIN_DISABLE_BEHAVIOUR.disablePlugins(pluginManager);"));
        Assert.assertFalse(section.contains("pluginManager.disablePlugins();"));

        Assert.assertTrue(behaviourText.contains("disablePlugins(PluginManager pluginManager)"));
        Assert.assertTrue(behaviourText.contains("pluginManager.disablePlugins();"));
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
