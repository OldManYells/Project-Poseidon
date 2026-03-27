package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat disconnect packet scaffold.
 */
public class Packet255KickDisconnect extends Packet {
    public final String reason;

    public Packet255KickDisconnect(String reason) {
        this.reason = reason;
    }
}

