package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat block-change packet scaffold.
 */
public class Packet53BlockChange extends Packet {
    public final int x;
    public final int y;
    public final int z;
    public final WorldServer world;
    public int material;
    public byte data;

    public Packet53BlockChange(int x, int y, int z, WorldServer world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.world = world;
    }
}
