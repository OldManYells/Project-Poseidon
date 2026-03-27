package com.legacyminecraft.poseidon.network;

import java.net.InetAddress;
import java.util.Map;

/**
 * Canonical throttling policy for rapid repeat connection attempts.
 */
public final class ConnectionAttemptThrottleSystem {
    private static final ConnectionAttemptThrottleSystem INSTANCE = new ConnectionAttemptThrottleSystem();
    private final ConnectionLoopbackPolicy connectionLoopbackPolicy = ConnectionLoopbackPolicy.getInstance();

    private ConnectionAttemptThrottleSystem() {
    }

    public static ConnectionAttemptThrottleSystem getInstance() {
        return INSTANCE;
    }

    public boolean shouldThrottle(Map connectionAttemptsByAddress, InetAddress address, long nowMillis, long cooldownMillis) {
        Long previousAttemptMillis = (Long) connectionAttemptsByAddress.get(address);
        if (previousAttemptMillis != null
                && !connectionLoopbackPolicy.isLoopback(address)
                && nowMillis - previousAttemptMillis.longValue() < cooldownMillis) {
            connectionAttemptsByAddress.put(address, Long.valueOf(nowMillis));
            return true;
        }

        connectionAttemptsByAddress.put(address, Long.valueOf(nowMillis));
        return false;
    }
}
