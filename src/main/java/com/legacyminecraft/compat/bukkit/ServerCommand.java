package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat server-command envelope scaffold.
 */
public class ServerCommand {
    public final String command;
    public final CommandSender source;

    public ServerCommand(String command, CommandSender source) {
        this.command = command;
        this.source = source;
    }
}

