package com.legacyminecraft.poseidon.network;

/**
 * Canonical threshold policy for keep-alive heartbeat cadence.
 */
public final class ConnectionHeartbeatThresholdPolicy {
    private static final ConnectionHeartbeatThresholdPolicy INSTANCE = new ConnectionHeartbeatThresholdPolicy();
    private static final int KEEP_ALIVE_THRESHOLD_TICKS = 20;

    private ConnectionHeartbeatThresholdPolicy() {
    }

    public static ConnectionHeartbeatThresholdPolicy getInstance() {
        return INSTANCE;
    }

    public int keepAliveThresholdTicks() {
        return KEEP_ALIVE_THRESHOLD_TICKS;
    }
}
