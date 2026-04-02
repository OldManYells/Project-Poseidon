package com.legacyminecraft.poseidon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

/**
 * Represents a class which contains a single method for executing commands
 */
public interface ICommandExecutor {

    /**
     * Executes the given command, returning its success
     *
     * @param sender Source of the command
     * @param command Command which was executed
     * @param label Alias of the command which was used
     * @param args Passed command arguments
     * @return true if a valid command, otherwise false
     */
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args);
}
