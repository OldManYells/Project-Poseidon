package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {
    public double a;

    public NBTTagDouble() {
    }

    public NBTTagDouble(double value) {
        this.a = value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeDouble(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        this.a = datainput.readDouble();
    }

    @Override
    public byte a() {
        return 6;
    }
}
