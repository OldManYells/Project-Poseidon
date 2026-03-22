package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet;

import java.util.List;

/**
 * Canonical enqueue orchestration for outbound packet queues.
 */
public final class OutboundQueueEnqueueExecutionSystem {
    private static final OutboundQueueEnqueueExecutionSystem INSTANCE = new OutboundQueueEnqueueExecutionSystem();

    private OutboundQueueEnqueueExecutionSystem() {
    }

    public static OutboundQueueEnqueueExecutionSystem getInstance() {
        return INSTANCE;
    }

    public EnqueueStepResult execute(
            boolean shuttingDown,
            Object queueLock,
            List highPriorityQueue,
            List lowPriorityQueue,
            Packet packet,
            int queuedBytes,
            OutboundQueueSystem outboundQueueSystem
    ) {
        if (shuttingDown) {
            return EnqueueStepResult.of(queuedBytes, false);
        }

        synchronized (queueLock) {
            OutboundQueueSystem.QueueState queueState =
                    outboundQueueSystem.enqueuePacket(highPriorityQueue, lowPriorityQueue, packet, queuedBytes);
            return EnqueueStepResult.of(queueState.getQueuedBytes(), true);
        }
    }

    public static final class EnqueueStepResult {
        private final int queuedBytes;
        private final boolean enqueued;

        private EnqueueStepResult(int queuedBytes, boolean enqueued) {
            this.queuedBytes = queuedBytes;
            this.enqueued = enqueued;
        }

        public static EnqueueStepResult of(int queuedBytes, boolean enqueued) {
            return new EnqueueStepResult(queuedBytes, enqueued);
        }

        public int getQueuedBytes() {
            return this.queuedBytes;
        }

        public boolean isEnqueued() {
            return this.enqueued;
        }
    }
}
