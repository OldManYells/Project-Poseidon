package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerReloadBootstrapWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_RELOAD_BOOTSTRAP_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerReloadBootstrapBehaviour.java");

    @Test
    public void craftServerDelegatesReloadBootstrapWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_RELOAD_BOOTSTRAP_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void reload() {", "private void loadCustomPermissions() {");

        Assert.assertTrue(craftServerText.contains("CraftServerReloadBootstrapBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RELOAD_BOOTSTRAP_BEHAVIOUR.bootstrap(new CraftServerReloadBootstrapBehaviour.ReloadBootstrapDelegate()"));

        Assert.assertFalse(section.contains("enablePlugins(PluginLoadOrder.STARTUP);"));
        Assert.assertFalse(section.contains("enablePlugins(PluginLoadOrder.POSTWORLD);"));

        Assert.assertTrue(behaviourText.contains("bootstrap(ReloadBootstrapDelegate delegate)"));
        Assert.assertTrue(behaviourText.contains("delegate.loadPlugins();"));
        Assert.assertTrue(behaviourText.contains("delegate.enablePlugins(PluginLoadOrder.STARTUP);"));
        Assert.assertTrue(behaviourText.contains("delegate.enablePlugins(PluginLoadOrder.POSTWORLD);"));
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
