package com.legacyminecraft.poseidon.network;

/**
 * Canonical run-loop for legacy network reader threads.
 */
public final class NetworkReaderLoopSystem {
    private static final NetworkReaderLoopSystem INSTANCE = new NetworkReaderLoopSystem();

    private NetworkReaderLoopSystem() {
    }

    public static NetworkReaderLoopSystem getInstance() {
        return INSTANCE;
    }

    public void runLoop(boolean fast, ReaderLoopOperations operations) {
        operations.incrementReaderThreadCount();

        try {
            while (true) {
                if (!operations.isConnectionOpen()) {
                    break;
                }

                if (operations.isShuttingDown()) {
                    break;
                }

                while (operations.readNextPacket()) {
                    ;
                }

                operations.sleepQuietly(fast ? 2L : 100L);
            }
        } finally {
            operations.decrementReaderThreadCount();
        }
    }

    public interface ReaderLoopOperations {
        void incrementReaderThreadCount();

        void decrementReaderThreadCount();

        boolean isConnectionOpen();

        boolean isShuttingDown();

        boolean readNextPacket();

        void sleepQuietly(long millis);
    }
}
