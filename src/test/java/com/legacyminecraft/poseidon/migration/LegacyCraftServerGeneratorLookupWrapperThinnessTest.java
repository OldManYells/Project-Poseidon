package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerGeneratorLookupWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_GENERATOR_LOOKUP_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerGeneratorLookupBehaviour.java");

    @Test
    public void craftServerDelegatesGeneratorLookupWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_GENERATOR_LOOKUP_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public ChunkGenerator getGenerator(String world) {", "public CraftMapView getMap(short id) {");

        Assert.assertTrue(craftServerText.contains("CraftServerGeneratorLookupBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_GENERATOR_LOOKUP_BEHAVIOUR.getGenerator(configuration, pluginManager, getLogger(), world)"));

        Assert.assertFalse(section.contains("configuration.getNode(\"worlds\")"));
        Assert.assertFalse(section.contains("name.split(\":\", 2)"));
        Assert.assertFalse(section.contains("pluginManager.getPlugin"));
        Assert.assertFalse(section.contains("logger.severe"));

        Assert.assertTrue(behaviourText.contains("getGenerator(Configuration configuration, PluginManager pluginManager, Logger logger, String worldName)"));
        Assert.assertTrue(behaviourText.contains("ConfigurationNode node = configuration.getNode(\"worlds\");"));
        Assert.assertTrue(behaviourText.contains("String[] split = name.split(\":\", 2);"));
        Assert.assertTrue(behaviourText.contains("Plugin plugin = pluginManager.getPlugin(split[0]);"));
        Assert.assertTrue(behaviourText.contains("logger.severe(\"Could not set generator for default world '\" + worldName + \"': Plugin '\" + split[0] + \"' does not exist\")"));
        Assert.assertTrue(behaviourText.contains("logger.severe(\"Could not set generator for default world '\" + worldName + \"': Plugin '\" + split[0] + \"' is not enabled yet (is it load:STARTUP?)\")"));
        Assert.assertTrue(behaviourText.contains("result = plugin.getDefaultWorldGenerator(worldName, id);"));
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
