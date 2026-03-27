package com.legacyminecraft.poseidon.runtime;

import com.legacyminecraft.compat.bukkit.CommandSenderBackedListener;

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
            senderName = resolveSenderName(((CommandSenderBackedListener) listener).getSender());
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

    private String resolveSenderName(Object sender) {
        if (sender == null) {
            return null;
        }
        try {
            Object value = sender.getClass().getMethod("getName").invoke(sender);
            return value == null ? null : value.toString();
        } catch (ReflectiveOperationException ignored) {
            return null;
        }
    }
}
