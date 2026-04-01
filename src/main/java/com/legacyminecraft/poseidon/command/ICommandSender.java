package com.legacyminecraft.poseidon.command;

import org.bukkit.Server;

public interface ICommandSender {

    /**
     * Sends this sender a message
     *
     * @param message Message to be displayed
     */
    public void sendMessage(String message);

    /**
     * Returns the server instance that this command is running on
     *
     * @return Server instance
     */
    public Server getServer();

    /**
     * Gets the name of this command sender
     *
     * @return Name of the sender
     */
    public String getName();
}
