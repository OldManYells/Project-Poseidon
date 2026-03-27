package com.legacyminecraft.poseidon.runtime;

import java.io.EOFException;
import java.io.IOException;
import java.util.Locale;

/**
 * Canonical policy for classifying expected console-reader shutdown exceptions.
 */
public final class ConsoleReaderExceptionPolicy {
    private static final ConsoleReaderExceptionPolicy INSTANCE = new ConsoleReaderExceptionPolicy();

    private ConsoleReaderExceptionPolicy() {
    }

    public static ConsoleReaderExceptionPolicy getInstance() {
        return INSTANCE;
    }

    public boolean isExpectedShutdownThrowable(Throwable throwable) {
        Throwable current = throwable;
        while (current != null) {
            if (current instanceof EOFException || current instanceof IOException || current instanceof InterruptedException) {
                String message = current.getMessage();
                if (message == null) {
                    return true;
                }
                String normalizedMessage = message.toLowerCase(Locale.ROOT);
                if (normalizedMessage.contains("closed")
                        || normalizedMessage.contains("end of stream")
                        || normalizedMessage.contains("stream closed")
                        || normalizedMessage.contains("interrupted")
                        || normalizedMessage.contains("shutdown")) {
                    return true;
                }
            }

            String message = current.getMessage();
            if (message != null) {
                String normalizedMessage = message.toLowerCase(Locale.ROOT);
                if (normalizedMessage.contains("console closed")
                        || normalizedMessage.contains("reader closed")) {
                    return true;
                }
            }

            current = current.getCause();
        }

        return false;
    }
}
