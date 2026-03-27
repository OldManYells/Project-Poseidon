package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerPermissionLoadWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_PERMISSION_LOAD_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerPermissionLoadBehaviour.java");

    @Test
    public void craftServerDelegatesCustomPermissionLoadingWrapperGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_PERMISSION_LOAD_BEHAVIOUR_PATH);
        String section = section(craftServerText, "private void loadCustomPermissions() {", "public World createWorld(String name, World.Environment environment) {");

        Assert.assertTrue(craftServerText.contains("CraftServerPermissionLoadBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_PERMISSION_LOAD_BEHAVIOUR.loadCustomPermissions(configuration, yaml, getLogger(), pluginManager);"));

        Assert.assertFalse(section.contains("new File(configuration.getString(\"settings.permissions-file\"))"));
        Assert.assertFalse(section.contains("new FileInputStream(file)"));
        Assert.assertFalse(section.contains("yaml.load(stream)"));
        Assert.assertFalse(section.contains("pluginManager.addPermission(Permission.loadPermission"));
        Assert.assertFalse(section.contains("Bukkit.getServer().getLogger().log(Level.SEVERE"));

        Assert.assertTrue(behaviourText.contains("loadCustomPermissions(Configuration configuration, Yaml yaml, Logger logger, PluginManager pluginManager)"));
        Assert.assertTrue(behaviourText.contains("File file = new File(configuration.getString(\"settings.permissions-file\"));"));
        Assert.assertTrue(behaviourText.contains("stream = new FileInputStream(file);"));
        Assert.assertTrue(behaviourText.contains("permissions = (Map<String, Map<String, Object>>) yaml.load(stream);"));
        Assert.assertTrue(behaviourText.contains("pluginManager.addPermission(Permission.loadPermission(name, permissions.get(name)));"));
        Assert.assertTrue(behaviourText.contains("Bukkit.getServer().getLogger().log(Level.SEVERE, \"Permission node '\" + name + \"' in server config is invalid\", ex);"));
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
