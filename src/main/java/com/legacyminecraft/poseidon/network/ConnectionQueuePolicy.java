package com.legacyminecraft.poseidon.network;

/**
 * Canonical queue policy for network overflow, timeout, and per-tick processing limits.
 */
public final class ConnectionQueuePolicy {
    private static final ConnectionQueuePolicy INSTANCE = new ConnectionQueuePolicy();
    private final ConnectionQueueThresholdPolicy connectionQueueThresholdPolicy =
            ConnectionQueueThresholdPolicy.getInstance();

    private ConnectionQueuePolicy() {
    }

    public static ConnectionQueuePolicy getInstance() {
        return INSTANCE;
    }

    public boolean isOverflow(int queuedBytes, boolean fastModeEnabled) {
        return queuedBytes > connectionQueueThresholdPolicy.overflowThreshold(fastModeEnabled);
    }

    public TimeoutDecision evaluateTimeout(boolean queueEmpty, int idleTicks, int timeoutThreshold) {
        if (!queueEmpty) {
            return new TimeoutDecision(false, 0);
        }

        boolean shouldDisconnect = idleTicks == timeoutThreshold;
        return new TimeoutDecision(shouldDisconnect, idleTicks + 1);
    }

    public int getProcessingBudget(boolean fastModeEnabled) {
        return connectionQueueThresholdPolicy.processingBudget(fastModeEnabled);
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
