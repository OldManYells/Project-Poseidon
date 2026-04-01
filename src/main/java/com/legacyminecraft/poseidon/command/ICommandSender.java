package com.legacyminecraft.poseidon.command;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

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
