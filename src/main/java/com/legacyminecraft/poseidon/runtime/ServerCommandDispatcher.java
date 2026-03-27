package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;

import java.util.List;

/**
 * Canonical command queue dispatcher used by legacy runtime wrappers.
 */
public final class ServerCommandDispatcher {
    private static final ServerCommandDispatcher INSTANCE = new ServerCommandDispatcher();
    private static final ServerCommandEnvelopeBehaviour SERVER_COMMAND_ENVELOPE_BEHAVIOUR =
            ServerCommandEnvelopeBehaviour.getInstance();
    private static final ServerCommandDispatchPolicy SERVER_COMMAND_DISPATCH_POLICY =
            ServerCommandDispatchPolicy.getInstance();

    private ServerCommandDispatcher() {
    }

    public static ServerCommandDispatcher getInstance() {
        return INSTANCE;
    }

    public void drainQueuedCommands(
            List<ServerCommandEnvelopeBehaviour.ServerCommandState> pendingCommands,
            ConsoleCommandSender consoleSender,
            Server server
    ) {
        while (pendingCommands.size() > 0) {
            ServerCommandEnvelopeBehaviour.ServerCommandState serverCommandState = pendingCommands.remove(0);

            // During early startup failure (for example bind failures), Bukkit may not be initialized yet.
            // Preserve shutdown stability by dropping queued console commands in this transient state.
            if (!SERVER_COMMAND_DISPATCH_POLICY.isBukkitReady(server, consoleSender)) {
                continue;
            }

            ServerCommandEvent event = new ServerCommandEvent(consoleSender, serverCommandState.getCommandText());
            server.getPluginManager().callEvent(event);
            String dispatchCommand = SERVER_COMMAND_DISPATCH_POLICY.resolveDispatchCommand(
                    event.getCommand(),
                    serverCommandState.getCommandText()
            );
            if (!SERVER_COMMAND_DISPATCH_POLICY.shouldDispatch(dispatchCommand)) {
                continue;
            }
            serverCommandState = SERVER_COMMAND_ENVELOPE_BEHAVIOUR.createState(
                    dispatchCommand,
                    serverCommandState.getCommandListener()
            );

            server.dispatchCommand(consoleSender, serverCommandState.getCommandText());
        }
    }
}
