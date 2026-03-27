package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local float NBT tag scaffold.
 */
public class NBTTagFloat extends NBTBase {
    public final float data;

    public NBTTagFloat(float data) {
        this.data = data;
    }
}
