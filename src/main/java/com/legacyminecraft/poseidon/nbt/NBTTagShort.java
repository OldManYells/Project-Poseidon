package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase {
    public short a;

    public NBTTagShort() {
    }

    public NBTTagShort(short value) {
        this.a = value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeShort(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        this.a = datainput.readShort();
    }

    @Override
    public byte a() {
        return 2;
    }
}
