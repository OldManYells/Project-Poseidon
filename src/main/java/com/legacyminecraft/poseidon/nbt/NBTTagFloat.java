package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {
    public float a;

    public NBTTagFloat() {
    }

    public NBTTagFloat(float value) {
        this.a = value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeFloat(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        this.a = datainput.readFloat();
    }

    @Override
    public byte a() {
        return 5;
    }
}
