package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for naming accepted inbound network connections.
 */
public final class NetworkConnectionLabelPolicy {
    private static final NetworkConnectionLabelPolicy INSTANCE = new NetworkConnectionLabelPolicy();
    private static final String CONNECTION_PREFIX = "Connection #";

    private NetworkConnectionLabelPolicy() {
    }

    public static NetworkConnectionLabelPolicy getInstance() {
        return INSTANCE;
    }

    public String buildConnectionLabel(int connectionNumber) {
        return CONNECTION_PREFIX + connectionNumber;
    }
}
