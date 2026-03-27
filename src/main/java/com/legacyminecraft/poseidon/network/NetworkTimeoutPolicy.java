package com.legacyminecraft.poseidon.network;

/**
 * Canonical timeout policy for network manager idle disconnect thresholds.
 */
public final class NetworkTimeoutPolicy {
    private static final NetworkTimeoutPolicy INSTANCE = new NetworkTimeoutPolicy();
    private static final int DEFAULT_IDLE_TIMEOUT_TICKS = 1200;

    private NetworkTimeoutPolicy() {
    }

    public static NetworkTimeoutPolicy getInstance() {
        return INSTANCE;
    }

    public int idleTimeoutTicks() {
        return DEFAULT_IDLE_TIMEOUT_TICKS;
    }
}
