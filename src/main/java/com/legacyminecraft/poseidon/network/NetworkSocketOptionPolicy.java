package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for network socket bootstrap options.
 */
public final class NetworkSocketOptionPolicy {
    private static final NetworkSocketOptionPolicy INSTANCE = new NetworkSocketOptionPolicy();
    private static final int TRAFFIC_CLASS = 24;
    private static final int SOCKET_TIMEOUT_MILLIS = 30000;
    private static final int OUTPUT_BUFFER_BYTES = 5120;

    private NetworkSocketOptionPolicy() {
    }

    public static NetworkSocketOptionPolicy getInstance() {
        return INSTANCE;
    }

    public int trafficClass() {
        return TRAFFIC_CLASS;
    }

    public int socketTimeoutMillis() {
        return SOCKET_TIMEOUT_MILLIS;
    }

    public int outputBufferBytes() {
        return OUTPUT_BUFFER_BYTES;
    }
}
