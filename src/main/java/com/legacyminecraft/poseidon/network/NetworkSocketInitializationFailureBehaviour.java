package com.legacyminecraft.poseidon.network;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Canonical behavior for network socket stream-initialization failure reporting.
 */
public final class NetworkSocketInitializationFailureBehaviour {
    private static final NetworkSocketInitializationFailureBehaviour INSTANCE =
            new NetworkSocketInitializationFailureBehaviour();

    private NetworkSocketInitializationFailureBehaviour() {
    }

    public static NetworkSocketInitializationFailureBehaviour getInstance() {
        return INSTANCE;
    }

    public String resolveMessage(IOException exception) {
        if (exception == null || exception.getMessage() == null || exception.getMessage().trim().isEmpty()) {
            return "Socket stream initialization failed";
        }
        return exception.getMessage();
    }

    public void logInitializationFailure(Logger logger, IOException exception) {
        if (logger == null) {
            return;
        }
        logger.log(Level.WARNING, resolveMessage(exception));
    }
}
