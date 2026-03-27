package com.legacyminecraft.poseidon.network;

/**
 * Network-local disconnect packet alias.
 */
public class Packet255KickDisconnect extends com.legacyminecraft.compat.bukkit.Packet255KickDisconnect {
    public Packet255KickDisconnect(String reason) {
        super(reason);
    }
}

