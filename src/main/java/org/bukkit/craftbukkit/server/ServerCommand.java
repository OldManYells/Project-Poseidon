package org.bukkit.craftbukkit.server;

import net.minecraft.server.ICommandListener;

public class ServerCommand {

    public final String command;
    public final ICommandListener b;

    public ServerCommand(String s, ICommandListener icommandlistener) {
        this.command = s;
        this.b = icommandlistener;
    }
}
