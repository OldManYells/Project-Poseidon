package com.legacyminecraft.poseidon.network;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical logging behavior for invalid-position exploit responses.
 */
public final class InvalidPositionLogBehaviour {
    private static final InvalidPositionLogBehaviour INSTANCE = new InvalidPositionLogBehaviour();

    private InvalidPositionLogBehaviour() {
    }

    public static InvalidPositionLogBehaviour getInstance() {
        return INSTANCE;
    }

    public void logInvalidPosition(Logger logger, String message) {
        if (logger == null || message == null) {
            return;
        }
        logger.log(Level.WARNING, message);
    }
}
