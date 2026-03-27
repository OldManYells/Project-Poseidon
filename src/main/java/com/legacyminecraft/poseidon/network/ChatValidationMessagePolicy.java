package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for inbound chat validation disconnect messages.
 */
public final class ChatValidationMessagePolicy {
    private static final ChatValidationMessagePolicy INSTANCE = new ChatValidationMessagePolicy();
    private static final String ILLEGAL_CHARACTERS = "Illegal characters in chat";
    private static final String CHAT_TOO_LONG = "Chat message too long";

    private ChatValidationMessagePolicy() {
    }

    public static ChatValidationMessagePolicy getInstance() {
        return INSTANCE;
    }

    public String illegalCharactersMessage() {
        return ILLEGAL_CHARACTERS;
    }

    public String chatTooLongMessage() {
        return CHAT_TOO_LONG;
    }
}
