package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;

import java.util.List;

/**
 * Canonical queueing behaviour for console/player-issued server commands.
 */
public final class ServerCommandQueueBehaviour {
    private static final ServerCommandQueueBehaviour INSTANCE = new ServerCommandQueueBehaviour();
    private static final ServerCommandEnvelopeBehaviour SERVER_COMMAND_ENVELOPE_BEHAVIOUR =
            ServerCommandEnvelopeBehaviour.getInstance();

    private ServerCommandQueueBehaviour() {
    }

    public static ServerCommandQueueBehaviour getInstance() {
        return INSTANCE;
    }

    public void enqueue(
            List<ServerCommandEnvelopeBehaviour.ServerCommandState> commandQueue,
            String commandText,
            ICommandListener commandListener
    ) {
        commandQueue.add(SERVER_COMMAND_ENVELOPE_BEHAVIOUR.createState(commandText, commandListener));
    }
}
