package com.legacyminecraft.poseidon.network;

/**
 * Canonical orchestration for inbound chat packet processing and action execution.
 */
public final class IncomingChatPacketExecutionSystem {
    private static final IncomingChatPacketExecutionSystem INSTANCE = new IncomingChatPacketExecutionSystem();

    private IncomingChatPacketExecutionSystem() {
    }

    public static IncomingChatPacketExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean execute(
            String rawMessage,
            int maxLength,
            String allowedCharacters,
            IncomingChatPacketHandler incomingChatPacketHandler,
            IncomingChatResultExecutionSystem incomingChatResultExecutionSystem,
            IncomingChatResultExecutionSystem.ChatActions chatActions
    ) {
        IncomingChatPacketHandler.IncomingChatResult result = incomingChatPacketHandler.processIncomingChat(
                rawMessage,
                maxLength,
                allowedCharacters
        );
        return incomingChatResultExecutionSystem.executeResult(result, chatActions);
    }
}
