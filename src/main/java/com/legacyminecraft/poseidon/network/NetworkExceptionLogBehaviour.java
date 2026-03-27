package com.legacyminecraft.poseidon.network;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical behavior for unexpected network exception logging.
 */
public final class NetworkExceptionLogBehaviour {
    private static final NetworkExceptionLogBehaviour INSTANCE = new NetworkExceptionLogBehaviour();

    private NetworkExceptionLogBehaviour() {
    }

    public static NetworkExceptionLogBehaviour getInstance() {
        return INSTANCE;
    }

    public void logUnexpectedException(Logger logger, Exception exception) {
        if (logger == null || exception == null) {
            return;
        }
        logger.log(Level.WARNING, "Unexpected network exception", exception);
    }
}
