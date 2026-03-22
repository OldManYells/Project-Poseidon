package com.legacyminecraft.poseidon.network;

import java.io.DataOutputStream;
import java.util.List;

/**
 * Canonical orchestration for one outbound queue drain step, including exception fallback semantics.
 */
public final class OutboundQueueDrainExecutionSystem {
    private static final OutboundQueueDrainExecutionSystem INSTANCE = new OutboundQueueDrainExecutionSystem();

    private OutboundQueueDrainExecutionSystem() {
    }

    public static OutboundQueueDrainExecutionSystem getInstance() {
        return INSTANCE;
    }

    public DrainStepResult execute(
            Object queueLock,
            List highPriorityQueue,
            List lowPriorityQueue,
            int queuedBytes,
            int lowPriorityQueueDelay,
            int sentPacketCounter,
            long currentTimeMillis,
            DataOutputStream output,
            int[] packetCounters,
            OutboundQueueSystem outboundQueueSystem,
            OutboundDrainActions outboundDrainActions
    ) {
        try {
            OutboundQueueSystem.DrainResult drainResult = outboundQueueSystem.drain(
                    queueLock,
                    highPriorityQueue,
                    lowPriorityQueue,
                    queuedBytes,
                    lowPriorityQueueDelay,
                    sentPacketCounter,
                    currentTimeMillis,
                    output,
                    packetCounters
            );
            return DrainStepResult.of(
                    drainResult.getQueuedBytes(),
                    drainResult.getLowPriorityQueueDelay(),
                    drainResult.wrotePacket()
            );
        } catch (Exception exception) {
            outboundDrainActions.handleException(exception);
            return DrainStepResult.of(queuedBytes, lowPriorityQueueDelay, false);
        }
    }

    public interface OutboundDrainActions {
        void handleException(Exception exception);
    }

    public static final class DrainStepResult {
        private final int queuedBytes;
        private final int lowPriorityQueueDelay;
        private final boolean wrotePacket;

        private DrainStepResult(int queuedBytes, int lowPriorityQueueDelay, boolean wrotePacket) {
            this.queuedBytes = queuedBytes;
            this.lowPriorityQueueDelay = lowPriorityQueueDelay;
            this.wrotePacket = wrotePacket;
        }

        public static DrainStepResult of(int queuedBytes, int lowPriorityQueueDelay, boolean wrotePacket) {
            return new DrainStepResult(queuedBytes, lowPriorityQueueDelay, wrotePacket);
        }

        public int getQueuedBytes() {
            return this.queuedBytes;
        }

        public int getLowPriorityQueueDelay() {
            return this.lowPriorityQueueDelay;
        }

        public boolean wrotePacket() {
            return this.wrotePacket;
        }
    }
}
