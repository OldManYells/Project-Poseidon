package com.legacyminecraft.poseidon.network;

/**
 * Canonical queue policy for network overflow, timeout, and per-tick processing limits.
 */
public final class ConnectionQueuePolicy {
    private static final ConnectionQueuePolicy INSTANCE = new ConnectionQueuePolicy();
    private static final int FAST_OVERFLOW_THRESHOLD = 2097152;
    private static final int STANDARD_OVERFLOW_THRESHOLD = 1048576;
    private static final int FAST_PROCESSING_BUDGET = 1000;
    private static final int STANDARD_PROCESSING_BUDGET = 100;

    private ConnectionQueuePolicy() {
    }

    public static ConnectionQueuePolicy getInstance() {
        return INSTANCE;
    }

    public boolean isOverflow(int queuedBytes, boolean fastModeEnabled) {
        return queuedBytes > (fastModeEnabled ? FAST_OVERFLOW_THRESHOLD : STANDARD_OVERFLOW_THRESHOLD);
    }

    public TimeoutDecision evaluateTimeout(boolean queueEmpty, int idleTicks, int timeoutThreshold) {
        if (!queueEmpty) {
            return new TimeoutDecision(false, 0);
        }

        boolean shouldDisconnect = idleTicks == timeoutThreshold;
        return new TimeoutDecision(shouldDisconnect, idleTicks + 1);
    }

    public int getProcessingBudget(boolean fastModeEnabled) {
        return fastModeEnabled ? FAST_PROCESSING_BUDGET : STANDARD_PROCESSING_BUDGET;
    }

    public static final class TimeoutDecision {
        private final boolean shouldDisconnect;
        private final int nextIdleTicks;

        private TimeoutDecision(boolean shouldDisconnect, int nextIdleTicks) {
            this.shouldDisconnect = shouldDisconnect;
            this.nextIdleTicks = nextIdleTicks;
        }

        public boolean shouldDisconnect() {
            return shouldDisconnect;
        }

        public int getNextIdleTicks() {
            return nextIdleTicks;
        }
    }
}
