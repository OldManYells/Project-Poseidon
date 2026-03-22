package net.minecraft.server;

import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;

public class ServerCommand {
    private static final ServerCommandEnvelopeBehaviour SERVER_COMMAND_ENVELOPE_BEHAVIOUR = ServerCommandEnvelopeBehaviour.getInstance();

    public final String command;
    public final ICommandListener b;

    public ServerCommand(String s, ICommandListener icommandlistener) {
        ServerCommandEnvelopeBehaviour.ServerCommandState state = SERVER_COMMAND_ENVELOPE_BEHAVIOUR.createState(s, icommandlistener);
        this.command = state.getCommandText();
        this.b = state.getCommandListener();
    }
}
