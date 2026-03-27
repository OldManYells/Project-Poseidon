package com.legacyminecraft.poseidon.network;

/**
 * Canonical key policy for network disconnect translation keys.
 */
public final class NetworkDisconnectKeyPolicy {
    private static final NetworkDisconnectKeyPolicy INSTANCE = new NetworkDisconnectKeyPolicy();

    private static final String DISCONNECT_OVERFLOW = "disconnect.overflow";
    private static final String DISCONNECT_TIMEOUT = "disconnect.timeout";
    private static final String DISCONNECT_SPAM = "disconnect.spam";
    private static final String DISCONNECT_QUITTING = "disconnect.quitting";
    private static final String DISCONNECT_CLOSED = "disconnect.closed";
    private static final String DISCONNECT_END_OF_STREAM = "disconnect.endOfStream";
    private static final String DISCONNECT_GENERIC_REASON = "disconnect.genericReason";

    private NetworkDisconnectKeyPolicy() {
    }

    public static NetworkDisconnectKeyPolicy getInstance() {
        return INSTANCE;
    }

    public String overflow() {
        return DISCONNECT_OVERFLOW;
    }

    public String timeout() {
        return DISCONNECT_TIMEOUT;
    }

    public String spam() {
        return DISCONNECT_SPAM;
    }

    public String quitting() {
        return DISCONNECT_QUITTING;
    }

    public String closed() {
        return DISCONNECT_CLOSED;
    }

    public String endOfStream() {
        return DISCONNECT_END_OF_STREAM;
    }

    public String genericReason() {
        return DISCONNECT_GENERIC_REASON;
    }
}
