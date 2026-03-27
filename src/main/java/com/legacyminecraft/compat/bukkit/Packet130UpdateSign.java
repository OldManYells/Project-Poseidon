package com.legacyminecraft.compat.bukkit;

public class Packet130UpdateSign extends Packet {
    public final int x;
    public final int y;
    public final int z;
    public final String[] lines;

    public Packet130UpdateSign(int x, int y, int z, String[] lines) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.lines = lines;
    }
}
