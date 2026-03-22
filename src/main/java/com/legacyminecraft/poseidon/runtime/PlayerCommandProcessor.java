package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.Poseidon;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.CommandException;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

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

    public void handlePlayerCommand(Server server, Player player, String command, Logger logger) {
        PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(player, command);
        server.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return;
        }

        String commandMessage = event.getMessage();

        try {
            if (server.dispatchCommand(player, commandMessage.substring(1))) {
                logDispatchedCommand(logger, player.getName(), commandMessage);
                return;
            }
        } catch (CommandException ex) {
            player.sendMessage(ChatColor.RED + "An internal error occurred while attempting to perform this command");
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
}
