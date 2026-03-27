package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat handshake packet scaffold.
 */
public class Packet2Handshake extends Packet {
    public final String token;

    public Packet2Handshake(String token) {
        this.token = token;
    }
}

