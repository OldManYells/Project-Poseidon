package com.legacyminecraft.poseidon.network;


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

    public QueueState enqueuePacket(List highPriorityQueue, List lowPriorityQueue, Object packet, int queuedBytes) {
        int updatedQueuedBytes = queuedBytes + Bridge.packetSize(packet) + 1;
        if (Bridge.isLowPriority(packet)) {
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

        if (!highPriorityQueue.isEmpty() && canWriteHighPriority(packetDelayMs, nowMillis, highPriorityQueue.get(0))) {
            Object packet = removeFirst(queueLock, highPriorityQueue);
            updatedQueuedBytes -= Bridge.packetSize(packet) + 1;
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
                && (highPriorityQueue.isEmpty() || isLowPriorityOlder(highPriorityQueue.get(0), lowPriorityQueue.get(0)))) {
            Object packet = removeFirst(queueLock, lowPriorityQueue);
            updatedQueuedBytes -= Bridge.packetSize(packet) + 1;
            writePacket(packet, output, outboundPacketBytes);
            updatedLowPriorityQueueDelay = 0;
            wrotePacket = true;
        }

        return new DrainResult(wrotePacket, updatedQueuedBytes, updatedLowPriorityQueueDelay);
    }

    private Object removeFirst(Object queueLock, List queue) {
        synchronized (queueLock) {
            return queue.remove(0);
        }
    }

    private boolean canWriteHighPriority(int packetDelayMs, long nowMillis, Object firstHighPriorityPacket) {
        return packetDelayMs == 0 || nowMillis - Bridge.packetTimestamp(firstHighPriorityPacket) >= (long) packetDelayMs;
    }

    private boolean isLowPriorityOlder(Object firstHighPriorityPacket, Object firstLowPriorityPacket) {
        return Bridge.packetTimestamp(firstHighPriorityPacket) > Bridge.packetTimestamp(firstLowPriorityPacket);
    }

    private void writePacket(Object packet, DataOutputStream output, int[] outboundPacketBytes) throws IOException {
        Bridge.writePacket(packet, output);
        outboundPacketBytes[Bridge.packetId(packet)] += Bridge.packetSize(packet) + 1;
    }

    private static final class Bridge {
        private static boolean isLowPriority(Object packet) {
            return readBooleanField(packet, "k");
        }

        private static long packetTimestamp(Object packet) {
            return readLongField(packet, "timestamp");
        }

        private static int packetId(Object packet) {
            return invokeInt(packet, "b");
        }

        private static int packetSize(Object packet) {
            return invokeInt(packet, "a");
        }

        private static void writePacket(Object packet, DataOutputStream output) throws IOException {
            NetworkCompatGatewayRegistry.gateway().writePacket(packet, output);
        }

        private static int invokeInt(Object target, String methodName) {
            try {
                java.lang.reflect.Method method = target.getClass().getMethod(methodName);
                return ((Integer) method.invoke(target)).intValue();
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static boolean readBooleanField(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                return field.getBoolean(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }

        private static long readLongField(Object target, String fieldName) {
            try {
                java.lang.reflect.Field field = target.getClass().getField(fieldName);
                return field.getLong(target);
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
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
