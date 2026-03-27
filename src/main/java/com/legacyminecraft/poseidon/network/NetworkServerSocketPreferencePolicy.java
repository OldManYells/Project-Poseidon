package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for server-socket performance preference tuple ordering.
 */
public final class NetworkServerSocketPreferencePolicy {
    private static final NetworkServerSocketPreferencePolicy INSTANCE = new NetworkServerSocketPreferencePolicy();
    private static final int CONNECTION_TIME = 0;
    private static final int LATENCY = 2;
    private static final int BANDWIDTH = 1;

    private NetworkServerSocketPreferencePolicy() {
    }

    public static NetworkServerSocketPreferencePolicy getInstance() {
        return INSTANCE;
    }

    public int connectionTimeWeight() {
        return CONNECTION_TIME;
    }

    public int latencyWeight() {
        return LATENCY;
    }

    public int bandwidthWeight() {
        return BANDWIDTH;
    }
}
