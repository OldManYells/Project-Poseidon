package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat chunk-coordinate value object.
 */
public class ChunkCoordinates {
    public final int x;
    public final int y;
    public final int z;

    public ChunkCoordinates(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
