package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerReloadPropertyManagerWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_RELOAD_PROPERTY_MANAGER_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerReloadPropertyManagerBehaviour.java");

    @Test
    public void craftServerDelegatesReloadPropertyManagerWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_RELOAD_PROPERTY_MANAGER_BEHAVIOUR_PATH);
        String section = section(craftServerText, "public void reload() {", "private void loadCustomPermissions() {");

        Assert.assertTrue(craftServerText.contains("CraftServerReloadPropertyManagerBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_RELOAD_PROPERTY_MANAGER_BEHAVIOUR.createAndApply(console)"));

        Assert.assertFalse(section.contains("PropertyManager config = new PropertyManager(console.options);"));
        Assert.assertFalse(section.contains("console.propertyManager = config;"));

        Assert.assertTrue(behaviourText.contains("createAndApply(MinecraftServer console)"));
        Assert.assertTrue(behaviourText.contains("PropertyManager config = new PropertyManager(console.options);"));
        Assert.assertTrue(behaviourText.contains("console.propertyManager = config;"));
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
