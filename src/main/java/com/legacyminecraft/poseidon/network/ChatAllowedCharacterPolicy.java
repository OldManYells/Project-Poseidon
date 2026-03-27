package com.legacyminecraft.poseidon.network;


/**
 * Canonical policy for inbound chat allowed-character set selection.
 */
public final class ChatAllowedCharacterPolicy {
    private static final ChatAllowedCharacterPolicy INSTANCE = new ChatAllowedCharacterPolicy();

    private ChatAllowedCharacterPolicy() {
    }

    public static ChatAllowedCharacterPolicy getInstance() {
        return INSTANCE;
    }

    public String allowedCharacters() {
        return NetworkCompatGatewayRegistry.gateway().allowedCharacters();
    }
}
