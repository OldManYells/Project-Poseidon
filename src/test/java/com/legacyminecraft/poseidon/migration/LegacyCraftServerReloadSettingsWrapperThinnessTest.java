package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerReloadSettingsWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_RELOAD_SETTINGS_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerReloadSettingsBehaviour.java");

    @Test
    public void craftServerDelegatesReloadSettingsWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_RELOAD_SETTINGS_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void reload() {", "private void loadCustomPermissions() {");

        Assert.assertTrue(craftServerText.contains("CraftServerReloadSettingsBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RELOAD_SETTINGS_BEHAVIOUR.applySettings(console, config);"));

        Assert.assertFalse(section.contains("boolean animals = config.getBoolean(\"spawn-animals\", console.spawnAnimals);"));
        Assert.assertFalse(section.contains("boolean monsters = config.getBoolean(\"spawn-monsters\", console.worlds.get(0).spawnMonsters > 0);"));
        Assert.assertFalse(section.contains("console.onlineMode = config.getBoolean(\"online-mode\", console.onlineMode);"));
        Assert.assertFalse(section.contains("for (WorldServer world : console.worlds) {"));

        Assert.assertTrue(behaviourText.contains("applySettings(MinecraftServer console, PropertyManager config)"));
        Assert.assertTrue(behaviourText.contains("boolean animals = config.getBoolean(\"spawn-animals\", console.spawnAnimals);"));
        Assert.assertTrue(behaviourText.contains("boolean monsters = config.getBoolean(\"spawn-monsters\", console.worlds.get(0).spawnMonsters > 0);"));
        Assert.assertTrue(behaviourText.contains("console.onlineMode = config.getBoolean(\"online-mode\", console.onlineMode);"));
        Assert.assertTrue(behaviourText.contains("for (WorldServer world : console.worlds) {"));
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
