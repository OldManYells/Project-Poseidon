package com.legacyminecraft.poseidon.network;

import net.minecraft.server.NetHandler;
import net.minecraft.server.Packet;

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

    public ReadDecision readNext(DataInputStream input, NetHandler handler, int[] inboundPacketBytes, List inboundQueue) throws IOException {
        Packet packet = Packet.a(input, handler.c());
        if (packet == null) {
            return ReadDecision.endOfStream();
        }

        inboundPacketBytes[packet.b()] += packet.a() + 1;
        inboundQueue.add(packet);
        return ReadDecision.packetQueued();
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
