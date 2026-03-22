package com.legacyminecraft.poseidon.network;

/**
 * Canonical service for inbound chat packet validation and normalization.
 */
public final class IncomingChatPacketHandler {
    private static final IncomingChatPacketHandler INSTANCE = new IncomingChatPacketHandler();
    private final ChatValidationPolicy chatValidationPolicy = ChatValidationPolicy.getInstance();

    private IncomingChatPacketHandler() {
    }

    public static IncomingChatPacketHandler getInstance() {
        return INSTANCE;
    }

    public IncomingChatResult processIncomingChat(String rawMessage, int maxLength, String allowedCharacters) {
        ChatValidationPolicy.ValidationResult validationResult =
                chatValidationPolicy.validateIncomingMessage(rawMessage, maxLength, allowedCharacters);
        if (!validationResult.isValid()) {
            return IncomingChatResult.invalid(validationResult.getKickMessage());
        }
        return IncomingChatResult.valid(validationResult.getNormalizedMessage());
    }

    public static final class IncomingChatResult {
        private final boolean valid;
        private final String normalizedMessage;
        private final String disconnectReason;

        private IncomingChatResult(boolean valid, String normalizedMessage, String disconnectReason) {
            this.valid = valid;
            this.normalizedMessage = normalizedMessage;
            this.disconnectReason = disconnectReason;
        }

        public static IncomingChatResult valid(String normalizedMessage) {
            return new IncomingChatResult(true, normalizedMessage, null);
        }

        public static IncomingChatResult invalid(String disconnectReason) {
            return new IncomingChatResult(false, null, disconnectReason);
        }

        public boolean isValid() {
            return valid;
        }

        public String getNormalizedMessage() {
            return normalizedMessage;
        }

        public String getDisconnectReason() {
            return disconnectReason;
        }
    }
}
