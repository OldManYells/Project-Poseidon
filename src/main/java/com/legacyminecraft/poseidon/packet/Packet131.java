package com.legacyminecraft.poseidon.packet;

/**
 * Canonical map-data packet scaffold.
 */
public class Packet131 extends Packet {
    public short itemId;
    public short itemData;
    public byte[] payload;

    public Packet131() {
        this((short) 0, (short) 0, new byte[0]);
    }

    public Packet131(short itemId, short itemData, byte[] payload) {
        this.itemId = itemId;
        this.itemData = itemData;
        this.payload = payload;
    }
}
