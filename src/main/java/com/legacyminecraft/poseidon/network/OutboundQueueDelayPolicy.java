package com.legacyminecraft.poseidon.network;

/**
 * Canonical policy for initial outbound low-priority queue delay.
 */
public final class OutboundQueueDelayPolicy {
    private static final OutboundQueueDelayPolicy INSTANCE = new OutboundQueueDelayPolicy();
    private static final int INITIAL_LOW_PRIORITY_QUEUE_DELAY = 50;

    private OutboundQueueDelayPolicy() {
    }

    public static OutboundQueueDelayPolicy getInstance() {
        return INSTANCE;
    }

    public int initialLowPriorityQueueDelay() {
        return INITIAL_LOW_PRIORITY_QUEUE_DELAY;
    }
}
