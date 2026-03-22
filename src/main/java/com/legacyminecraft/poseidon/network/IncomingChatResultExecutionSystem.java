package com.legacyminecraft.poseidon.network;

/**
 * Canonical execution flow for processed incoming chat validation results.
 */
public final class IncomingChatResultExecutionSystem {
    private static final IncomingChatResultExecutionSystem INSTANCE = new IncomingChatResultExecutionSystem();

    private IncomingChatResultExecutionSystem() {
    }

    public static IncomingChatResultExecutionSystem getInstance() {
        return INSTANCE;
    }

    public boolean executeResult(
            IncomingChatPacketHandler.IncomingChatResult incomingChatResult,
            ChatActions chatActions
    ) {
        if (!incomingChatResult.isValid()) {
            chatActions.disconnect(incomingChatResult.getDisconnectReason());
            return false;
        }

        chatActions.dispatchNormalizedChat(incomingChatResult.getNormalizedMessage());
        return true;
    }

    public interface ChatActions {
        void disconnect(String message);

        void dispatchNormalizedChat(String normalizedMessage);
    }
}
