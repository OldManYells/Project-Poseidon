package com.legacyminecraft.poseidon.server.command;
import com.legacyminecraft.poseidon.api.*;
import com.legacyminecraft.poseidon.world.entity.data.*;
import com.legacyminecraft.poseidon.statistics.*;
import com.legacyminecraft.poseidon.world.pathfinding.*;
import com.legacyminecraft.poseidon.world.physics.*;
import com.legacyminecraft.poseidon.world.math.*;

public class ServerCommand {

    public final String command;
    public final ICommandListener b;

    public ServerCommand(String s, ICommandListener icommandlistener) {
        this.command = s;
        this.b = icommandlistener;
    }
}
