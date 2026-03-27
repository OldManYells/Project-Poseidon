package com.legacyminecraft.poseidon.network;

/**
 * Canonical run-loop for legacy network reader threads.
 */
public final class NetworkReaderLoopSystem {
    private static final NetworkReaderLoopSystem INSTANCE = new NetworkReaderLoopSystem();
    private final NetworkLoopPausePolicy networkLoopPausePolicy = NetworkLoopPausePolicy.getInstance();

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

                try {
                    while (operations.readNextPacket()) {
                        ;
                    }
                } catch (Exception exception) {
                    if (!operations.isShuttingDown()) {
                        operations.handleException(exception);
                    }
                }

                operations.sleepQuietly(fast
                        ? networkLoopPausePolicy.fastLoopPauseMillis()
                        : networkLoopPausePolicy.standardLoopPauseMillis());
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

        void handleException(Exception exception);
    }
}
