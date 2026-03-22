package com.legacyminecraft.poseidon.packet;

import java.util.HashMap;
import java.util.Map;

/**
 * Canonical packet traffic counter aggregator.
 */
public final class PacketTrafficCounter {
    private final Map packetCounters = new HashMap();
    private int packetCounterSampleCount = 0;

    public synchronized void recordPacketStat(Integer packetId, int packetSize) {
        PacketCounterSnapshot packetCounterSnapshot = (PacketCounterSnapshot) packetCounters.get(packetId);
        if (packetCounterSnapshot == null) {
            packetCounterSnapshot = new PacketCounterSnapshot();
            packetCounters.put(packetId, packetCounterSnapshot);
        }

        packetCounterSnapshot.record(packetSize);
        ++packetCounterSampleCount;
        if (packetCounterSampleCount % 1000 == 0) {
            ;
        }
    }

    private static final class PacketCounterSnapshot {
        private int packetCount;
        private long totalPayloadBytes;

        private void record(int payloadBytes) {
            ++packetCount;
            totalPayloadBytes += (long) payloadBytes;
        }
    }
}
