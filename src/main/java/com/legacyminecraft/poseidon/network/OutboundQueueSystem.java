package com.legacyminecraft.poseidon.network;

import net.minecraft.server.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Canonical queueing and drain logic for NetworkManager outbound packet queues.
 */
public final class OutboundQueueSystem {
    private static final OutboundQueueSystem INSTANCE = new OutboundQueueSystem();

    private OutboundQueueSystem() {
    }

    public static OutboundQueueSystem getInstance() {
        return INSTANCE;
    }

    public QueueState enqueuePacket(List highPriorityQueue, List lowPriorityQueue, Packet packet, int queuedBytes) {
        int updatedQueuedBytes = queuedBytes + packet.a() + 1;
        if (packet.k) {
            lowPriorityQueue.add(packet);
        } else {
            highPriorityQueue.add(packet);
        }
        return new QueueState(updatedQueuedBytes);
    }

    public DrainResult drain(
            Object queueLock,
            List highPriorityQueue,
            List lowPriorityQueue,
            int queuedBytes,
            int lowPriorityQueueDelay,
            int packetDelayMs,
            long nowMillis,
            DataOutputStream output,
            int[] outboundPacketBytes
    ) throws IOException {
        boolean wrotePacket = false;
        int updatedQueuedBytes = queuedBytes;
        int updatedLowPriorityQueueDelay = lowPriorityQueueDelay;

        if (!highPriorityQueue.isEmpty() && canWriteHighPriority(packetDelayMs, nowMillis, (Packet) highPriorityQueue.get(0))) {
            Packet packet = removeFirst(queueLock, highPriorityQueue);
            updatedQueuedBytes -= packet.a() + 1;
            writePacket(packet, output, outboundPacketBytes);
            wrotePacket = true;
        }

        boolean allowLowPriorityDrain;
        if (wrotePacket) {
            allowLowPriorityDrain = true;
        } else {
            allowLowPriorityDrain = updatedLowPriorityQueueDelay-- <= 0;
        }

        if (allowLowPriorityDrain
                && !lowPriorityQueue.isEmpty()
                && (highPriorityQueue.isEmpty() || isLowPriorityOlder((Packet) highPriorityQueue.get(0), (Packet) lowPriorityQueue.get(0)))) {
            Packet packet = removeFirst(queueLock, lowPriorityQueue);
            updatedQueuedBytes -= packet.a() + 1;
            writePacket(packet, output, outboundPacketBytes);
            updatedLowPriorityQueueDelay = 0;
            wrotePacket = true;
        }

        return new DrainResult(wrotePacket, updatedQueuedBytes, updatedLowPriorityQueueDelay);
    }

    private Packet removeFirst(Object queueLock, List queue) {
        synchronized (queueLock) {
            return (Packet) queue.remove(0);
        }
    }

    private boolean canWriteHighPriority(int packetDelayMs, long nowMillis, Packet firstHighPriorityPacket) {
        return packetDelayMs == 0 || nowMillis - firstHighPriorityPacket.timestamp >= (long) packetDelayMs;
    }

    private boolean isLowPriorityOlder(Packet firstHighPriorityPacket, Packet firstLowPriorityPacket) {
        return firstHighPriorityPacket.timestamp > firstLowPriorityPacket.timestamp;
    }

    private void writePacket(Packet packet, DataOutputStream output, int[] outboundPacketBytes) throws IOException {
        Packet.a(packet, output);
        outboundPacketBytes[packet.b()] += packet.a() + 1;
    }

    public static final class QueueState {
        private final int queuedBytes;

        private QueueState(int queuedBytes) {
            this.queuedBytes = queuedBytes;
        }

        public int getQueuedBytes() {
            return queuedBytes;
        }
    }

    public static final class DrainResult {
        private final boolean wrotePacket;
        private final int queuedBytes;
        private final int lowPriorityQueueDelay;

        private DrainResult(boolean wrotePacket, int queuedBytes, int lowPriorityQueueDelay) {
            this.wrotePacket = wrotePacket;
            this.queuedBytes = queuedBytes;
            this.lowPriorityQueueDelay = lowPriorityQueueDelay;
        }

        public boolean wrotePacket() {
            return wrotePacket;
        }

        public int getQueuedBytes() {
            return queuedBytes;
        }

        public int getLowPriorityQueueDelay() {
            return lowPriorityQueueDelay;
        }
    }
}
