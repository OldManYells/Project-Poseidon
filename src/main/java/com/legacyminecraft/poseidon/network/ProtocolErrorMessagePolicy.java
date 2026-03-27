package com.legacyminecraft.poseidon.network;

/**
 * Canonical message policy for protocol error disconnects.
 */
public final class ProtocolErrorMessagePolicy {
    private static final ProtocolErrorMessagePolicy INSTANCE = new ProtocolErrorMessagePolicy();
    private static final String PROTOCOL_ERROR_KICK_MESSAGE = "Protocol error, unexpected packet";

    private ProtocolErrorMessagePolicy() {
    }

    public static ProtocolErrorMessagePolicy getInstance() {
        return INSTANCE;
    }

    public String protocolErrorKickMessage() {
        return PROTOCOL_ERROR_KICK_MESSAGE;
    }
}
