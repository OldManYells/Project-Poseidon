package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.runtime.command.ServerCommandEnvelopeBehaviour;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.server.ServerCommandEvent;

import java.util.List;

/**
 * Canonical command queue dispatcher used by legacy runtime wrappers.
 */
public final class ServerCommandDispatcher {
    private static final ServerCommandDispatcher INSTANCE = new ServerCommandDispatcher();
    private static final ServerCommandEnvelopeBehaviour SERVER_COMMAND_ENVELOPE_BEHAVIOUR =
            ServerCommandEnvelopeBehaviour.getInstance();

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
            if (server == null || consoleSender == null) {
                continue;
            }

            ServerCommandEvent event = new ServerCommandEvent(consoleSender, serverCommandState.getCommandText());
            server.getPluginManager().callEvent(event);
            serverCommandState = SERVER_COMMAND_ENVELOPE_BEHAVIOUR.createState(
                    event.getCommand(),
                    serverCommandState.getCommandListener()
            );

            server.dispatchCommand(consoleSender, serverCommandState.getCommandText());
        }
    }
}
