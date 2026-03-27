package com.legacyminecraft.compat.bukkit;

public class Packet15Place extends Packet {
    public final int x;
    public final int y;
    public final int z;
    public final int face;
    public final ItemStack itemstack;

    public Packet15Place(int x, int y, int z, int face, ItemStack itemstack) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.face = face;
        this.itemstack = itemstack;
    }
}
