package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.Poseidon;
import com.legacyminecraft.poseidon.network.NetworkCompatGatewayRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical player-command preprocessing and dispatch flow.
 */
public final class PlayerCommandProcessor {
    private static final PlayerCommandProcessor INSTANCE = new PlayerCommandProcessor();

    private PlayerCommandProcessor() {
    }

    public static PlayerCommandProcessor getInstance() {
        return INSTANCE;
    }

    public void handlePlayerCommand(Object server, Object player, String command, Logger logger) {
        Object event = NetworkCompatGatewayRegistry.gateway().createPlayerCommandPreprocessEvent(player, command);
        Object pluginManager = invoke(server, "getPluginManager");
        invoke(pluginManager, "callEvent", event);

        if (Boolean.TRUE.equals(invoke(event, "isCancelled"))) {
            return;
        }

        String commandMessage = String.valueOf(invoke(event, "getMessage"));

        try {
            if (Boolean.TRUE.equals(invoke(server, "dispatchCommand", player, commandMessage.substring(1)))) {
                logDispatchedCommand(logger, String.valueOf(invoke(player, "getName")), commandMessage);
                return;
            }
        } catch (Exception ex) {
            invoke(player, "sendMessage", "An internal error occurred while attempting to perform this command");
            Logger.getLogger(PlayerCommandProcessor.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }
    }

    public void logDispatchedCommand(Logger logger, String playerName, String commandMessage) {
        String commandName = commandMessage.split(" ")[0].replaceAll("/", "");

        if (shouldRedactCommand(commandName)) {
            logger.info(playerName + " issued server command: COMMAND REDACTED");
        } else {
            logger.info(playerName + " issued server command: " + commandMessage);
        }
    }

    public boolean shouldRedactCommand(String commandName) {
        return Poseidon.getServer() != null && Poseidon.getServer().isCommandHidden(commandName);
    }

    private Object invoke(Object target, String methodName, Object... args) {
        try {
            for (java.lang.reflect.Method method : target.getClass().getMethods()) {
                if (method.getName().equals(methodName) && method.getParameterTypes().length == args.length) {
                    method.setAccessible(true);
                    return method.invoke(target, args);
                }
            }
            throw new IllegalStateException("Method not found: " + methodName);
        } catch (Exception exception) {
            throw new IllegalStateException(exception);
        }
    }

}
