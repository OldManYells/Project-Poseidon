package com.legacyminecraft.poseidon.network;

/**
 * Canonical argument policy for disconnect key parameter payloads.
 */
public final class NetworkDisconnectArgumentPolicy {
    private static final NetworkDisconnectArgumentPolicy INSTANCE = new NetworkDisconnectArgumentPolicy();
    private static final Object[] EMPTY_ARGS = new Object[0];

    private NetworkDisconnectArgumentPolicy() {
    }

    public static NetworkDisconnectArgumentPolicy getInstance() {
        return INSTANCE;
    }

    public Object[] emptyArgs() {
        return EMPTY_ARGS;
    }

    public Object[] genericReasonArgs(String reason) {
        return new Object[]{reason};
    }
}
