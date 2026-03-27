package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase {
    public byte a;

    public NBTTagByte() {
    }

    public NBTTagByte(byte value) {
        this.a = value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeByte(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        this.a = datainput.readByte();
    }

    @Override
    public byte a() {
        return 1;
    }
}
