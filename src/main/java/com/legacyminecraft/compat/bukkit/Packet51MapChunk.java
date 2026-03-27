package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat chunk packet scaffold.
 */
public class Packet51MapChunk extends Packet {
    public final int x;
    public final int y;
    public final int z;
    public final int sizeX;
    public final int sizeY;
    public final int sizeZ;
    public final byte[] data;

    public Packet51MapChunk(int x, int y, int z, int sizeX, int sizeY, int sizeZ, byte[] data) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;
        this.data = data;
    }
}
