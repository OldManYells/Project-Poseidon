package com.legacyminecraft.poseidon.network;

import java.io.EOFException;
import java.net.SocketException;
import java.nio.channels.ClosedChannelException;
import java.util.Locale;

/**
 * Canonical exception routing for network wrappers.
 */
public final class NetworkExceptionDisconnectSystem {
    private static final NetworkExceptionDisconnectSystem INSTANCE = new NetworkExceptionDisconnectSystem();

    private NetworkExceptionDisconnectSystem() {
    }

    public static NetworkExceptionDisconnectSystem getInstance() {
        return INSTANCE;
    }

    public void execute(Exception exception, ExceptionActions exceptionActions) {
        boolean expectedDisconnectException = isExpectedDisconnectException(exception);
        if (!expectedDisconnectException) {
            exceptionActions.printStackTrace(exception);
        }

        exceptionActions.disconnectWithGenericReason(resolveDisconnectReason(exception, expectedDisconnectException));
    }

    public boolean isExpectedDisconnectException(Exception exception) {
        if (exception instanceof SocketException || exception instanceof EOFException || exception instanceof ClosedChannelException) {
            return true;
        }

        String message = exception.getMessage();
        if (message == null) {
            return false;
        }

        String normalizedMessage = message.toLowerCase(Locale.ROOT);
        return normalizedMessage.contains("socket closed")
                || normalizedMessage.contains("broken pipe")
                || normalizedMessage.contains("connection reset")
                || normalizedMessage.contains("connection aborted");
    }

    public String resolveDisconnectReason(Exception exception, boolean expectedDisconnectException) {
        if (expectedDisconnectException) {
            return "Connection closed";
        }
        return "Internal exception: " + exception.toString();
    }

    public interface ExceptionActions {
        void printStackTrace(Exception exception);

        void disconnectWithGenericReason(String reason);
    }
}
