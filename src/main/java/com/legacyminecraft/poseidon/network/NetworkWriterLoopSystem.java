package com.legacyminecraft.poseidon.network;

/**
 * Canonical run-loop for legacy network writer threads.
 */
public final class NetworkWriterLoopSystem {
    private static final NetworkWriterLoopSystem INSTANCE = new NetworkWriterLoopSystem();
    private final NetworkLoopPausePolicy networkLoopPausePolicy = NetworkLoopPausePolicy.getInstance();
    private final NetworkExceptionDisconnectSystem networkExceptionDisconnectSystem =
            NetworkExceptionDisconnectSystem.getInstance();

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
                    operations.sleepQuietly(networkLoopPausePolicy.standardLoopPauseMillis());
                }

                try {
                    operations.flushOutput();
                } catch (Exception exception) {
                    boolean expectedDisconnectException =
                            networkExceptionDisconnectSystem.isExpectedDisconnectException(exception);
                    if (!operations.isShuttingDown()) {
                        operations.handleException(exception);
                    }
                    if (expectedDisconnectException) {
                        break;
                    }
                }

                if (fast) {
                    operations.sleepQuietly(networkLoopPausePolicy.fastLoopPauseMillis());
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

        void flushOutput();

        boolean isShuttingDown();

        void handleException(Exception exception);
    }
}
