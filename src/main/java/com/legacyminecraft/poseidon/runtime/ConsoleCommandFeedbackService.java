package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.poseidon.compat.bukkit.CommandSenderBackedListener;
import net.minecraft.server.EntityPlayer;
import net.minecraft.server.ICommandListener;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.Packet3Chat;
import org.bukkit.entity.Player;

import java.util.logging.Logger;

/**
 * Canonical feedback and operator-broadcast flow for legacy console command wrappers.
 */
public final class ConsoleCommandFeedbackService {
    private static final ConsoleCommandFeedbackService INSTANCE = new ConsoleCommandFeedbackService();

    private ConsoleCommandFeedbackService() {
    }

    public static ConsoleCommandFeedbackService getInstance() {
        return INSTANCE;
    }

    public void print(ICommandListener listener, MinecraftServer server, Logger logger, String sourceName, String message) {
        String formatted = sourceName + ": " + message;

        listener.sendMessage(message);
        informOps(listener, server, "\u00A77(" + formatted + ")");
        if (listener instanceof MinecraftServer) {
            return;
        }

        logger.info(formatted);
    }

    public void informOps(ICommandListener listener, MinecraftServer server, String message) {
        Packet3Chat packet3chat = new Packet3Chat(message);
        String senderName = null;
        if (listener instanceof CommandSenderBackedListener) {
            org.bukkit.command.CommandSender commandSender = ((CommandSenderBackedListener) listener).getSender();
            if (commandSender instanceof Player) {
                senderName = ((Player) commandSender).getName();
            }
        }

        java.util.List<EntityPlayer> players = server.serverConfigurationManager.players;
        for (int i = 0; i < players.size(); ++i) {
            EntityPlayer entityPlayer = (EntityPlayer) players.get(i);
            if ((senderName == null || !senderName.equalsIgnoreCase(entityPlayer.name))
                    && server.serverConfigurationManager.isOp(entityPlayer.name)) {
                entityPlayer.netServerHandler.sendPacket(packet3chat);
            }
        }
    }
}
