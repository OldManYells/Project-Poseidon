package net.minecraft.server;

import com.legacyminecraft.poseidon.packet.PacketCounterBehaviour;

class PacketCounter {
    private static final PacketCounterBehaviour PACKET_COUNTER_BEHAVIOUR = PacketCounterBehaviour.getInstance();

    private int a;
    private long b;

    private PacketCounter() {}

    public void a(int i) {
        PacketCounterBehaviour.Snapshot snapshot = PACKET_COUNTER_BEHAVIOUR.increment(this.a, this.b, i);
        this.a = snapshot.packetCount;
        this.b = snapshot.totalBytes;
    }

    PacketCounter(EmptyClass1 emptyclass1) {
        this();
    }
}
