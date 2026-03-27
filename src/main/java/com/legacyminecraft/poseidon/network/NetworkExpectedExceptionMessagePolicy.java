package com.legacyminecraft.poseidon.network;

import java.util.Locale;

/**
 * Canonical message matcher for expected socket disconnect exception text.
 */
public final class NetworkExpectedExceptionMessagePolicy {
    private static final NetworkExpectedExceptionMessagePolicy INSTANCE = new NetworkExpectedExceptionMessagePolicy();

    private NetworkExpectedExceptionMessagePolicy() {
    }

    public static NetworkExpectedExceptionMessagePolicy getInstance() {
        return INSTANCE;
    }

    public boolean isExpectedDisconnectMessage(String message) {
        if (message == null) {
            return false;
        }

        String normalizedMessage = message.toLowerCase(Locale.ROOT);
        return normalizedMessage.contains("socket closed")
                || normalizedMessage.contains("broken pipe")
                || normalizedMessage.contains("connection reset")
                || normalizedMessage.contains("connection aborted")
                || normalizedMessage.contains("timed out");
    }
}
