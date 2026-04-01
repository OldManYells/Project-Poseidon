package com.legacyminecraft.poseidon.packets;
import com.legacyminecraft.poseidon.*;

class PacketCounter {

    private int a;
    private long b;

    PacketCounter() {}

    public void a(int i) {
        ++this.a;
        this.b += (long) i;
    }

}
