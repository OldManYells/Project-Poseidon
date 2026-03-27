package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat effect packet scaffold.
 */
public class Packet61 extends Packet {
    public final int effectId;
    public final int x;
    public final int y;
    public final int z;
    public final int data;

    public Packet61(int effectId, int x, int y, int z, int data) {
        this.effectId = effectId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.data = data;
    }
}
