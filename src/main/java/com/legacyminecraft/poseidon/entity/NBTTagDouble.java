package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local double NBT tag scaffold.
 */
public class NBTTagDouble extends NBTBase {
    public final double data;

    public NBTTagDouble(double data) {
        this.data = data;
    }
}
