package com.legacyminecraft.poseidon.network;

/**
 * Canonical validation policy for inbound chat payloads.
 */
public final class ChatValidationPolicy {
    private static final ChatValidationPolicy INSTANCE = new ChatValidationPolicy();

    private ChatValidationPolicy() {
    }

    public static ChatValidationPolicy getInstance() {
        return INSTANCE;
    }

    public ValidationResult validateIncomingMessage(String rawMessage, int maxLength, String allowedCharacters) {
        if (rawMessage == null) {
            return ValidationResult.invalid("Illegal characters in chat");
        }

        if (rawMessage.length() > maxLength) {
            return ValidationResult.invalid("Chat message too long");
        }

        String normalized = rawMessage.trim();
        for (int i = 0; i < normalized.length(); ++i) {
            if (allowedCharacters.indexOf(normalized.charAt(i)) < 0) {
                return ValidationResult.invalid("Illegal characters in chat");
            }
        }

        return ValidationResult.valid(normalized);
    }

    public static final class ValidationResult {
        private final boolean valid;
        private final String normalizedMessage;
        private final String kickMessage;

        private ValidationResult(boolean valid, String normalizedMessage, String kickMessage) {
            this.valid = valid;
            this.normalizedMessage = normalizedMessage;
            this.kickMessage = kickMessage;
        }

        public static ValidationResult valid(String normalizedMessage) {
            return new ValidationResult(true, normalizedMessage, null);
        }

        public static ValidationResult invalid(String kickMessage) {
            return new ValidationResult(false, null, kickMessage);
        }

        public boolean isValid() {
            return valid;
        }

        public String getNormalizedMessage() {
            return normalizedMessage;
        }

        public String getKickMessage() {
            return kickMessage;
        }
    }
}
