package com.legacyminecraft.poseidon.network;

/**
 * Canonical reason text policy for disconnect messages shown to connection handlers.
 */
public final class NetworkDisconnectReasonPolicy {
    private static final NetworkDisconnectReasonPolicy INSTANCE = new NetworkDisconnectReasonPolicy();
    private static final String CONNECTION_CLOSED = "Connection closed";
    private static final String INTERNAL_EXCEPTION_PREFIX = "Internal exception: ";

    private NetworkDisconnectReasonPolicy() {
    }

    public static NetworkDisconnectReasonPolicy getInstance() {
        return INSTANCE;
    }

    public String connectionClosed() {
        return CONNECTION_CLOSED;
    }

    public String internalExceptionPrefix() {
        return INTERNAL_EXCEPTION_PREFIX;
    }
}
