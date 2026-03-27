package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Canonical NBT end-tag scaffold.
 */
public class NBTTagEnd extends NBTBase {
    @Override
    void a(DataOutput dataoutput) throws IOException {
    }

    @Override
    void a(DataInput datainput) throws IOException {
    }

    @Override
    public byte a() {
        return 0;
    }

    public byte getTypeId() {
        return 0;
    }

    public NBTBase clone() {
        return new NBTTagEnd();
    }
}
