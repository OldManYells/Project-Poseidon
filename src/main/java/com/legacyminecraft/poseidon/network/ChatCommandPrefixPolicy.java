package com.legacyminecraft.poseidon.network;

/**
 * Canonical prefix policy for command-vs-chat message routing.
 */
public final class ChatCommandPrefixPolicy {
    private static final ChatCommandPrefixPolicy INSTANCE = new ChatCommandPrefixPolicy();
    private static final String COMMAND_PREFIX = "/";

    private ChatCommandPrefixPolicy() {
    }

    public static ChatCommandPrefixPolicy getInstance() {
        return INSTANCE;
    }

    public String commandPrefix() {
        return COMMAND_PREFIX;
    }

    public boolean isCommand(String message) {
        return message != null && message.startsWith(COMMAND_PREFIX);
    }
}
