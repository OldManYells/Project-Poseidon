package com.legacyminecraft.poseidon.network;

import java.io.EOFException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.channels.ClosedChannelException;

/**
 * Canonical exception routing for network wrappers.
 */
public final class NetworkExceptionDisconnectSystem {
    private static final NetworkExceptionDisconnectSystem INSTANCE = new NetworkExceptionDisconnectSystem();
    private final NetworkExpectedExceptionMessagePolicy networkExpectedExceptionMessagePolicy =
            NetworkExpectedExceptionMessagePolicy.getInstance();
    private final NetworkDisconnectReasonPolicy networkDisconnectReasonPolicy =
            NetworkDisconnectReasonPolicy.getInstance();

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
        Throwable current = exception;
        while (current != null) {
            if (current instanceof SocketException
                    || current instanceof SocketTimeoutException
                    || current instanceof EOFException
                    || current instanceof ClosedChannelException) {
                return true;
            }

            String message = current.getMessage();
            if (networkExpectedExceptionMessagePolicy.isExpectedDisconnectMessage(message)) {
                return true;
            }

            current = current.getCause();
        }

        return false;
    }

    public String resolveDisconnectReason(Exception exception, boolean expectedDisconnectException) {
        if (expectedDisconnectException) {
            return networkDisconnectReasonPolicy.connectionClosed();
        }
        return networkDisconnectReasonPolicy.internalExceptionPrefix() + exception;
    }

    public interface ExceptionActions {
        void printStackTrace(Exception exception);

        void disconnectWithGenericReason(String reason);
    }
}
