package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerConfigLoadWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_CONFIG_LOAD_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerConfigLoadBehaviour.java");

    @Test
    public void craftServerDelegatesConfigLoadWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_CONFIG_LOAD_BEHAVIOUR_PATH);
        String section = section(craftServerText, "private void loadConfig() {", "public void loadPlugins() {");

        Assert.assertTrue(craftServerText.contains("CraftServerConfigLoadBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_CONFIG_LOAD_BEHAVIOUR.loadConfig(configuration);"));

        Assert.assertFalse(section.contains("configuration.load();"));
        Assert.assertFalse(section.contains("configuration.getString(\"database.url\", \"jdbc:sqlite:{DIR}{NAME}.db\")"));
        Assert.assertFalse(section.contains("configuration.getNode(\"aliases\")"));
        Assert.assertFalse(section.contains("configuration.setProperty(\"aliases.icanhasbukkit\", icanhasbukkit);"));
        Assert.assertFalse(section.contains("configuration.save();"));

        Assert.assertTrue(behaviourText.contains("loadConfig(Configuration configuration)"));
        Assert.assertTrue(behaviourText.contains("configuration.load();"));
        Assert.assertTrue(behaviourText.contains("configuration.getString(\"database.url\", \"jdbc:sqlite:{DIR}{NAME}.db\");"));
        Assert.assertTrue(behaviourText.contains("if (configuration.getNode(\"aliases\") == null) {"));
        Assert.assertTrue(behaviourText.contains("configuration.setProperty(\"aliases.icanhasbukkit\", icanhasbukkit);"));
        Assert.assertTrue(behaviourText.contains("configuration.save();"));
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
