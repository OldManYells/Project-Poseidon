package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerServiceAccessWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_SERVICE_ACCESS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerServiceAccessBehaviour.java");

    @Test
    public void craftServerDelegatesServiceAccessorWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_SERVICE_ACCESS_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public PluginManager getPluginManager() {", "public List<World> getWorlds() {");

        Assert.assertTrue(craftServerText.contains("CraftServerServiceAccessBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SERVICE_ACCESS_BEHAVIOUR.getPluginManager(pluginManager)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SERVICE_ACCESS_BEHAVIOUR.getScheduler(scheduler)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SERVICE_ACCESS_BEHAVIOUR.getServicesManager(servicesManager)"));

        Assert.assertFalse(section.contains("return pluginManager;"));
        Assert.assertFalse(section.contains("return scheduler;"));
        Assert.assertFalse(section.contains("return servicesManager;"));

        Assert.assertTrue(behaviourText.contains("getPluginManager(PluginManager pluginManager)"));
        Assert.assertTrue(behaviourText.contains("return pluginManager;"));
        Assert.assertTrue(behaviourText.contains("getScheduler(BukkitScheduler scheduler)"));
        Assert.assertTrue(behaviourText.contains("return scheduler;"));
        Assert.assertTrue(behaviourText.contains("getServicesManager(ServicesManager servicesManager)"));
        Assert.assertTrue(behaviourText.contains("return servicesManager;"));
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
