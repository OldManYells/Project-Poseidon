package com.legacyminecraft.poseidon.migration;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class LegacyCraftServerCommandLookupWrapperThinnessTest {
    private static final Path CRAFT_SERVER_PATH = Paths.get("src/main/java/org/bukkit/craftbukkit/CraftServer.java");
    private static final Path CRAFT_SERVER_COMMAND_LOOKUP_BEHAVIOUR_PATH =
            Paths.get("src/main/java/com/legacyminecraft/poseidon/compat/bukkit/CraftServerCommandLookupBehaviour.java");

    @Test
    public void craftServerDelegatesPluginCommandAndAliasLookupGlueToCanonicalBehaviour() throws IOException {
        String craftServerText = read(CRAFT_SERVER_PATH);
        String behaviourText = read(CRAFT_SERVER_COMMAND_LOOKUP_BEHAVIOUR_PATH);

        String pluginCommandSection = section(craftServerText,
                "public PluginCommand getPluginCommand(String name) {",
                "public void savePlayers() {");
        String commandAliasesSection = section(craftServerText,
                "public Map<String, String[]> getCommandAliases() {",
                "public int getSpawnRadius() {");

        Assert.assertTrue(craftServerText.contains("CraftServerCommandLookupBehaviour"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_COMMAND_LOOKUP_BEHAVIOUR.getPluginCommand(commandMap, name)"));
        Assert.assertTrue(craftServerText.contains("CRAFT_SERVER_COMMAND_LOOKUP_BEHAVIOUR.getCommandAliases(configuration.getNode(\"aliases\"))"));
        Assert.assertFalse(pluginCommandSection.contains("commandMap.getCommand(name)"));
        Assert.assertFalse(pluginCommandSection.contains("instanceof PluginCommand"));
        Assert.assertFalse(pluginCommandSection.contains("return null;"));
        Assert.assertFalse(commandAliasesSection.contains("ConfigurationNode node = configuration.getNode(\"aliases\");"));
        Assert.assertFalse(commandAliasesSection.contains("for (String key : node.getKeys())"));
        Assert.assertFalse(commandAliasesSection.contains("result.put(key, commands.toArray(new String[0]))"));

        Assert.assertTrue(behaviourText.contains("getPluginCommand(SimpleCommandMap commandMap, String name)"));
        Assert.assertTrue(behaviourText.contains("commandMap.getCommand(name)"));
        Assert.assertTrue(behaviourText.contains("instanceof PluginCommand"));
        Assert.assertTrue(behaviourText.contains("return null;"));
        Assert.assertTrue(behaviourText.contains("getCommandAliases(ConfigurationNode aliasNode)"));
        Assert.assertTrue(behaviourText.contains("aliasNode.getKeys()"));
        Assert.assertTrue(behaviourText.contains("aliasNode.getStringList(key, null)"));
        Assert.assertTrue(behaviourText.contains("commands.add(aliasNode.getString(key));"));
        Assert.assertTrue(behaviourText.contains("result.put(key, commands.toArray(new String[0]))"));
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
