package com.legacyminecraft.poseidon.packet;

public final class PacketCounterBehaviour {
    private static final PacketCounterBehaviour INSTANCE = new PacketCounterBehaviour();

    private PacketCounterBehaviour() {
    }

    public static PacketCounterBehaviour getInstance() {
        return INSTANCE;
    }

    public Snapshot increment(int packetCount, long totalBytes, int packetSize) {
        return new Snapshot(packetCount + 1, totalBytes + (long) packetSize);
    }

    public static final class Snapshot {
        public final int packetCount;
        public final long totalBytes;

        Snapshot(int packetCount, long totalBytes) {
            this.packetCount = packetCount;
            this.totalBytes = totalBytes;
        }
    }
}
