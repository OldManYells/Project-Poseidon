package com.legacyminecraft.poseidon.network;

import java.util.logging.Logger;

/**
 * Canonical behavior for NetworkManager console/info log routing.
 */
public final class NetworkManagerConsoleLogBehaviour {
    private static final NetworkManagerConsoleLogBehaviour INSTANCE = new NetworkManagerConsoleLogBehaviour();

    private NetworkManagerConsoleLogBehaviour() {
    }

    public static NetworkManagerConsoleLogBehaviour getInstance() {
        return INSTANCE;
    }

    public void log(Logger logger, String message) {
        if (logger == null || message == null) {
            return;
        }
        logger.info(message);
    }
}
