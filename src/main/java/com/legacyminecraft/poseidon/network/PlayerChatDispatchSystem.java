package com.legacyminecraft.poseidon.network;

import net.minecraft.server.MinecraftServer;
import org.bukkit.Server;
import org.bukkit.entity.Player;

/**
 * Canonical dispatcher for validated player chat messages and command routing.
 */
public final class PlayerChatDispatchSystem {
    private static final PlayerChatDispatchSystem INSTANCE = new PlayerChatDispatchSystem();
    private final ChatMessageRouter chatMessageRouter = ChatMessageRouter.getInstance();

    private PlayerChatDispatchSystem() {
    }

    public static PlayerChatDispatchSystem getInstance() {
        return INSTANCE;
    }

    public boolean dispatchValidatedChat(
            Server server,
            MinecraftServer minecraftServer,
            Player player,
            boolean playerDead,
            String message,
            CommandDispatcher commandDispatcher
    ) {
        if (playerDead) {
            return false;
        }

        if (chatMessageRouter.isCommandMessage(message)) {
            commandDispatcher.dispatch(message);
            return true;
        }

        return chatMessageRouter.handleNonCommandChat(server, minecraftServer, player, message);
    }

    public interface CommandDispatcher {
        void dispatch(String message);
    }
}
