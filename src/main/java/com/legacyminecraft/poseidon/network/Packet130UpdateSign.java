package com.legacyminecraft.poseidon.network;

/**
 * Network-local packet130 update-sign alias.
 */
public class Packet130UpdateSign extends com.legacyminecraft.compat.bukkit.Packet130UpdateSign {
    public Packet130UpdateSign(int x, int y, int z, String[] lines) {
        super(x, y, z, lines);
    }
}

