package com.legacyminecraft.poseidon.network;

import java.io.IOException;

/**
 * Canonical run-loop for legacy network writer threads.
 */
public final class NetworkWriterLoopSystem {
    private static final NetworkWriterLoopSystem INSTANCE = new NetworkWriterLoopSystem();

    private NetworkWriterLoopSystem() {
    }

    public static NetworkWriterLoopSystem getInstance() {
        return INSTANCE;
    }

    public void runLoop(boolean fast, WriterLoopOperations operations) {
        operations.incrementWriterThreadCount();

        try {
            while (true) {
                if (!operations.isConnectionOpen()) {
                    break;
                }

                while (operations.writeNextPacket()) {
                    ;
                }

                if (!fast) {
                    operations.sleepQuietly(100L);
                }

                try {
                    operations.flushOutput();
                } catch (IOException ioexception) {
                    if (!operations.isShuttingDown()) {
                        operations.handleException(ioexception);
                    }
                }

                if (fast) {
                    operations.sleepQuietly(2L);
                }
            }
        } finally {
            operations.decrementWriterThreadCount();
        }
    }

    public interface WriterLoopOperations {
        void incrementWriterThreadCount();

        void decrementWriterThreadCount();

        boolean isConnectionOpen();

        boolean writeNextPacket();

        void sleepQuietly(long millis);

        void flushOutput() throws IOException;

        boolean isShuttingDown();

        void handleException(Exception exception);
    }
}
