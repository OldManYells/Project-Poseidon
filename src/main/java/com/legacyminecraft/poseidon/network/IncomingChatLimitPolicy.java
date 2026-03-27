package com.legacyminecraft.poseidon.network;

/**
 * Canonical limit policy for inbound chat packet message length.
 */
public final class IncomingChatLimitPolicy {
    private static final IncomingChatLimitPolicy INSTANCE = new IncomingChatLimitPolicy();
    private static final int MAX_CHAT_LENGTH = 100;

    private IncomingChatLimitPolicy() {
    }

    public static IncomingChatLimitPolicy getInstance() {
        return INSTANCE;
    }

    public int maxChatLength() {
        return MAX_CHAT_LENGTH;
    }
}
