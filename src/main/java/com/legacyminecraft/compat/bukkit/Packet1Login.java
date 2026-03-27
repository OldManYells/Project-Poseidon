package com.legacyminecraft.compat.bukkit;

/**
 * Canonical compat login packet scaffold.
 */
public class Packet1Login extends Packet {
    public String name;
    public int entityId;
    public long seed;
    public byte dimension;

    public Packet1Login() {
    }

    public Packet1Login(String name, int entityId, long seed, byte dimension) {
        this.name = name;
        this.entityId = entityId;
        this.seed = seed;
        this.dimension = dimension;
    }
}

