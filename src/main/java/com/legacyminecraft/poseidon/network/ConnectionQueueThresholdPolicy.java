package com.legacyminecraft.poseidon.network;

/**
 * Canonical threshold policy for queued-byte overflow and per-tick processing budgets.
 */
public final class ConnectionQueueThresholdPolicy {
    private static final ConnectionQueueThresholdPolicy INSTANCE = new ConnectionQueueThresholdPolicy();
    private static final int FAST_OVERFLOW_THRESHOLD = 2097152;
    private static final int STANDARD_OVERFLOW_THRESHOLD = 1048576;
    private static final int FAST_PROCESSING_BUDGET = 1000;
    private static final int STANDARD_PROCESSING_BUDGET = 100;

    private ConnectionQueueThresholdPolicy() {
    }

    public static ConnectionQueueThresholdPolicy getInstance() {
        return INSTANCE;
    }

    public int overflowThreshold(boolean fastModeEnabled) {
        return fastModeEnabled ? FAST_OVERFLOW_THRESHOLD : STANDARD_OVERFLOW_THRESHOLD;
    }

    public int processingBudget(boolean fastModeEnabled) {
        return fastModeEnabled ? FAST_PROCESSING_BUDGET : STANDARD_PROCESSING_BUDGET;
    }
}
