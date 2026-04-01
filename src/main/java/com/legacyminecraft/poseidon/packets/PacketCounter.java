package com.legacyminecraft.poseidon.packets;

class PacketCounter {

    private int a;
    private long b;

    PacketCounter() {}

    public void a(int i) {
        ++this.a;
        this.b += (long) i;
    }

}
