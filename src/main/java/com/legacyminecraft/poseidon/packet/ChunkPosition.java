package com.legacyminecraft.poseidon.packet;

/**
 * Canonical packet-space chunk position alias.
 */
public class ChunkPosition {
    public final int x;
    public final int y;
    public final int z;

    public ChunkPosition(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
