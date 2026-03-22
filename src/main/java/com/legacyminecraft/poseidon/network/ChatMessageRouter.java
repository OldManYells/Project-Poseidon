package com.legacyminecraft.poseidon.network;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerChatEvent;

/**
 * Canonical router for player chat message flow.
 */
public final class ChatMessageRouter {
    private static final ChatMessageRouter INSTANCE = new ChatMessageRouter();

    private ChatMessageRouter() {
    }

    public static ChatMessageRouter getInstance() {
        return INSTANCE;
    }

    public boolean isCommandMessage(String message) {
        return message.startsWith("/");
    }

    public boolean handleNonCommandChat(Server server, MinecraftServer minecraftServer, Player player, String message) {
        PlayerChatEvent event = new PlayerChatEvent(player, message);
        server.getPluginManager().callEvent(event);

        if (event.isCancelled()) {
            return true;
        }

        String formatted = String.format(event.getFormat(), event.getPlayer().getDisplayName(), event.getMessage());
        minecraftServer.console.sendMessage(formatted);
        for (Player recipient : event.getRecipients()) {
            recipient.sendMessage(formatted);
        }
        return false;
    }
}
