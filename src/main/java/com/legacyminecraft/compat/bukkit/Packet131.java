package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat map-data packet scaffold.
 */
public class Packet131 extends com.legacyminecraft.poseidon.packet.Packet131 {
    public Packet131(short itemId, short itemData, byte[] payload) {
        super(itemId, itemData, payload);
    }
}
