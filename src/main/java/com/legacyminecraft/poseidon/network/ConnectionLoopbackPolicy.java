package com.legacyminecraft.poseidon.network;

import java.net.InetAddress;

/**
 * Canonical policy for loopback-address detection in connection throttling.
 */
public final class ConnectionLoopbackPolicy {
    private static final ConnectionLoopbackPolicy INSTANCE = new ConnectionLoopbackPolicy();
    private static final String LOOPBACK_IP = "127.0.0.1";

    private ConnectionLoopbackPolicy() {
    }

    public static ConnectionLoopbackPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isLoopback(InetAddress address) {
        return address != null && LOOPBACK_IP.equals(address.getHostAddress());
    }
}
