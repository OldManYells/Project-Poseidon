package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for connection-accept throttling windows.
 */
public final class ConnectionAcceptThrottlePolicy {
    private static final ConnectionAcceptThrottlePolicy INSTANCE = new ConnectionAcceptThrottlePolicy();
    private static final long DEFAULT_THROTTLE_MILLIS = 5000L;

    private ConnectionAcceptThrottlePolicy() {
    }

    public static ConnectionAcceptThrottlePolicy getInstance() {
        return INSTANCE;
    }

    public long defaultThrottleMillis() {
        return DEFAULT_THROTTLE_MILLIS;
    }
}
