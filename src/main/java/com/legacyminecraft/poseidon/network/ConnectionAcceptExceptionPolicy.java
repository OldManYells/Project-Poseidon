package com.legacyminecraft.poseidon.network;

import java.io.IOException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

/**
 * Canonical policy for classifying expected accept-loop shutdown exceptions.
 */
public final class ConnectionAcceptExceptionPolicy {
    private static final ConnectionAcceptExceptionPolicy INSTANCE = new ConnectionAcceptExceptionPolicy();

    private ConnectionAcceptExceptionPolicy() {
    }

    public static ConnectionAcceptExceptionPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isExpectedShutdownException(IOException exception) {
        Throwable current = exception;
        while (current != null) {
            if (current instanceof SocketException || current instanceof ClosedChannelException) {
                return true;
            }

            String message = current.getMessage();
            if (message != null) {
                String normalizedMessage = message.toLowerCase(Locale.ROOT);
                if (normalizedMessage.contains("socket closed")
                        || normalizedMessage.contains("closed")
                        || normalizedMessage.contains("interrupted")
                        || normalizedMessage.contains("shutdown")) {
                    return true;
                }
            }

            current = current.getCause();
        }

        return false;
    }
}
