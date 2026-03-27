package com.legacyminecraft.poseidon.network;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical console-log sink behavior for ground movement diagnostics.
 */
public final class GroundMovementConsoleLogBehaviour {
    private static final GroundMovementConsoleLogBehaviour INSTANCE = new GroundMovementConsoleLogBehaviour();

    private GroundMovementConsoleLogBehaviour() {
    }

    public static GroundMovementConsoleLogBehaviour getInstance() {
        return INSTANCE;
    }

    public void log(Logger logger, String line) {
        if (logger == null || line == null) {
            return;
        }
        logger.log(Level.INFO, line);
    }
}
