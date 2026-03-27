package com.legacyminecraft.poseidon.network;


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
            Object server,
            Object minecraftServer,
            Object player,
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
