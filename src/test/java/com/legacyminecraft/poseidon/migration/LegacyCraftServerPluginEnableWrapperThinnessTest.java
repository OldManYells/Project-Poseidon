package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerPluginEnableWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_PLUGIN_ENABLE_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPluginEnableBehaviour.java");

    @Test
    public void craftServerDelegatesPluginEnableWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_PLUGIN_ENABLE_BEHAVIOUR_PATH);
        String section = section(craftServerText, "private void loadPlugin(Plugin plugin) {", "public String getGameVersion() {");

        Assert.assertTrue(craftServerText.contains("CraftServerPluginEnableBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PLUGIN_ENABLE_BEHAVIOUR.loadPlugin(pluginManager, plugin, getLogger());"));

        Assert.assertFalse(section.contains("pluginManager.enablePlugin(plugin);"));
        Assert.assertFalse(section.contains("pluginManager.addPermission"));
        Assert.assertFalse(section.contains("plugin.getDescription().getPermissions()"));
        Assert.assertFalse(section.contains("tried to register permission"));

        Assert.assertTrue(behaviourText.contains("loadPlugin(PluginManager pluginManager, Plugin plugin, Logger logger)"));
        Assert.assertTrue(behaviourText.contains("pluginManager.enablePlugin(plugin);"));
        Assert.assertTrue(behaviourText.contains("List<Permission> permissions = plugin.getDescription().getPermissions();"));
        Assert.assertTrue(behaviourText.contains("pluginManager.addPermission(permission);"));
        Assert.assertTrue(behaviourText.contains("tried to register permission '\" + permission.getName() + \"' but it's already registered"));
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
