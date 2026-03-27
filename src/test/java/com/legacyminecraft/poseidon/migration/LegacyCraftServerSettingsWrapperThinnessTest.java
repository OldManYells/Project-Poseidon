package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerSettingsWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_SETTINGS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerSettingsBehaviour.java");

    @Test
    public void craftServerDelegatesSettingsWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_SETTINGS_BEHAVIOUR_PATH);
        String settingsSection = section(craftServerText, "public String getUpdateFolder() {", "public boolean getOnlineMode() {");

        Assert.assertTrue(craftServerText.contains("CraftServerSettingsBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SETTINGS_BEHAVIOUR.getUpdateFolder(configuration)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SETTINGS_BEHAVIOUR.getSpawnRadius(configuration)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_SETTINGS_BEHAVIOUR.setSpawnRadius(configuration, value)"));

        Assert.assertFalse(settingsSection.contains("configuration.getString(\"settings.update-folder\", \"update\")"));
        Assert.assertFalse(settingsSection.contains("configuration.getInt(\"settings.spawn-radius\", 16)"));
        Assert.assertFalse(settingsSection.contains("configuration.setProperty(\"settings.spawn-radius\", value);"));
        Assert.assertFalse(settingsSection.contains("configuration.save();"));

        Assert.assertTrue(behaviourText.contains("getUpdateFolder(Configuration configuration)"));
        Assert.assertTrue(behaviourText.contains("configuration.getString(\"settings.update-folder\", \"update\")"));
        Assert.assertTrue(behaviourText.contains("getSpawnRadius(Configuration configuration)"));
        Assert.assertTrue(behaviourText.contains("configuration.getInt(\"settings.spawn-radius\", 16)"));
        Assert.assertTrue(behaviourText.contains("setSpawnRadius(Configuration configuration, int value)"));
        Assert.assertTrue(behaviourText.contains("configuration.setProperty(\"settings.spawn-radius\", value);"));
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
