package com.legacyminecraft.poseidon.server.command;

import com.legacyminecraft.poseidon.api.ICommandListener;

public class ServerCommand {

    public final String command;
    public final ICommandListener b;

    public ServerCommand(String s, ICommandListener icommandlistener) {
        this.command = s;
        this.b = icommandlistener;
    }
}
