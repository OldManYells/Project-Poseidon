package com.legacyminecraft.poseidon.network;

/**
 * Canonical parser for extracting host addresses from legacy socket-string formats.
 */
public final class ConnectionAddressParser {
    private ConnectionAddressParser() {
    }

    public static String extractHost(String socketAddress) {
        if (socketAddress == null) {
            return "";
        }

        try {
            String host = socketAddress.substring(socketAddress.indexOf("/") + 1);
            return host.substring(0, host.indexOf(":"));
        } catch (Exception ignored) {
            return socketAddress;
        }
    }
}
