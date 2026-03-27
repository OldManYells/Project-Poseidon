package com.legacyminecraft.poseidon.network;


import java.io.DataInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Canonical reader for inbound network packets and queue accounting.
 */
public final class InboundQueueReadSystem {
    private static final InboundQueueReadSystem INSTANCE = new InboundQueueReadSystem();

    private InboundQueueReadSystem() {
    }

    public static InboundQueueReadSystem getInstance() {
        return INSTANCE;
    }

    public ReadDecision readNext(
            DataInputStream input,
            Object handler,
            int[] inboundPacketBytes,
            List inboundQueue,
            PacketReader packetReader
    ) throws IOException {
        Object packet = packetReader.read(input, handler);
        if (packet == null) {
            return ReadDecision.endOfStream();
        }

        inboundPacketBytes[Bridge.packetId(packet)] += Bridge.packetSize(packet) + 1;
        inboundQueue.add(packet);
        return ReadDecision.packetQueued();
    }

    private static final class Bridge {
        private static int packetId(Object packet) {
            return invokeInt(packet, "b");
        }

        private static int packetSize(Object packet) {
            return invokeInt(packet, "a");
        }

        private static int invokeInt(Object target, String methodName) {
            try {
                java.lang.reflect.Method method = target.getClass().getMethod(methodName);
                Object value = method.invoke(target);
                return ((Integer) value).intValue();
            } catch (Exception exception) {
                throw new IllegalStateException(exception);
            }
        }
    }

    public interface PacketReader {
        Object read(DataInputStream input, Object handler) throws IOException;
    }

    public static final class ReadDecision {
        private final boolean packetQueued;
        private final boolean endOfStream;

        private ReadDecision(boolean packetQueued, boolean endOfStream) {
            this.packetQueued = packetQueued;
            this.endOfStream = endOfStream;
        }

        public static ReadDecision packetQueued() {
            return new ReadDecision(true, false);
        }

        public static ReadDecision endOfStream() {
            return new ReadDecision(false, true);
        }

        public boolean isPacketQueued() {
            return packetQueued;
        }

        public boolean isEndOfStream() {
            return endOfStream;
        }
    }
}
