package com.legacyminecraft.poseidon.runtime;

import java.util.logging.Logger;

/**
 * Canonical execution flow for server console message logging.
 */
public final class ServerConsoleMessageLogSystem {
    private static final ServerConsoleMessageLogSystem INSTANCE = new ServerConsoleMessageLogSystem();

    private ServerConsoleMessageLogSystem() {
    }

    public static ServerConsoleMessageLogSystem getInstance() {
        return INSTANCE;
    }

    public void logInfo(String message, Logger logger) {
        logger.info(message);
    }

    public void logWarning(String message, Logger logger) {
        logger.warning(message);
    }
}
