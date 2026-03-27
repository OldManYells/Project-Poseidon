package com.legacyminecraft.compat.bukkit;

public class Packet9Respawn extends Packet {
    public final int dimension;

    public Packet9Respawn(int dimension) {
        this.dimension = dimension;
    }
}
