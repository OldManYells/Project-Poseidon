package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat spawn-position packet scaffold.
 */
public class Packet6SpawnPosition {
    public final int x;
    public final int y;
    public final int z;

    public Packet6SpawnPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
