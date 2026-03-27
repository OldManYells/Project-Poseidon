package com.legacyminecraft.poseidon.network;

/**
 * Canonical config-key policy for network transport and packet throughput toggles.
 */
public final class NetworkTransportConfigPolicy {
    private static final NetworkTransportConfigPolicy INSTANCE = new NetworkTransportConfigPolicy();
    private static final String TCP_NODELAY_KEY = "settings.enable-tpc-nodelay";
    private static final boolean TCP_NODELAY_DEFAULT = false;
    private static final String FASTER_PACKETS_KEY = "settings.faster-packets.enabled";
    private static final boolean FASTER_PACKETS_DEFAULT = true;

    private NetworkTransportConfigPolicy() {
    }

    public static NetworkTransportConfigPolicy getInstance() {
        return INSTANCE;
    }

    public String tcpNoDelayKey() {
        return TCP_NODELAY_KEY;
    }

    public boolean tcpNoDelayDefault() {
        return TCP_NODELAY_DEFAULT;
    }

    public String fasterPacketsEnabledKey() {
        return FASTER_PACKETS_KEY;
    }

    public boolean fasterPacketsEnabledDefault() {
        return FASTER_PACKETS_DEFAULT;
    }
}
