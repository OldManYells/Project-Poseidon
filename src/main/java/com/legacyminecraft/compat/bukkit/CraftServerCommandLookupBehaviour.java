package com.legacyminecraft.compat.bukkit;


import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Canonical behaviour for CraftServer command lookup and alias wrapper glue.
 */
public final class CraftServerCommandLookupBehaviour {
    private static final CraftServerCommandLookupBehaviour INSTANCE = new CraftServerCommandLookupBehaviour();

    private CraftServerCommandLookupBehaviour() {
    }

    public static CraftServerCommandLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public PluginCommand getPluginCommand(SimpleCommandMap commandMap, String name) {
        Command command = commandMap.getCommand(name);

        if (command instanceof PluginCommand) {
            return (PluginCommand) command;
        }

        return null;
    }

    public Map<String, String[]> getCommandAliases(ConfigurationNode aliasNode) {
        Map<String, String[]> result = new LinkedHashMap<String, String[]>();

        if (aliasNode != null) {
            for (String key : aliasNode.getKeys()) {
                List<String> commands = new ArrayList<String>();

                if (aliasNode.getProperty(key) instanceof List) {
                    commands = aliasNode.getStringList(key, null);
                } else {
                    commands.add(aliasNode.getString(key));
                }

                result.put(key, commands.toArray(new String[0]));
            }
        }

        return result;
    }
}
