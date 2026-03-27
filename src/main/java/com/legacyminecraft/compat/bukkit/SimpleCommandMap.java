package com.legacyminecraft.compat.bukkit;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Canonical compat simple-command-map scaffold.
 */
public class SimpleCommandMap {
    private final Map<String, Command> commands = new HashMap<String, Command>();

    public Command getCommand(String name) {
        if (name == null) {
            return null;
        }
        return commands.get(name.toLowerCase(Locale.ROOT));
    }

    public boolean dispatch(CommandSender sender, String commandLine) {
        return commandLine != null && commandLine.trim().length() > 0;
    }

    public void clearCommands() {
        commands.clear();
    }
}

