package com.legacyminecraft.poseidon.network;

/**
 * Canonical delay policy for close-monitor watchdog checks.
 */
public final class ConnectionMonitorDelayPolicy {
    private static final ConnectionMonitorDelayPolicy INSTANCE = new ConnectionMonitorDelayPolicy();
    private static final long WATCHDOG_DELAY_MILLIS = 2000L;

    private ConnectionMonitorDelayPolicy() {
    }

    public static ConnectionMonitorDelayPolicy getInstance() {
        return INSTANCE;
    }

    public long watchdogDelayMillis() {
        return WATCHDOG_DELAY_MILLIS;
    }
}
