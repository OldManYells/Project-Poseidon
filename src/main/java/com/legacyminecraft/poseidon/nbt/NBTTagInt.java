package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase {
    public int a;

    public NBTTagInt() {
    }

    public NBTTagInt(int value) {
        this.a = value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        this.a = datainput.readInt();
    }

    @Override
    public byte a() {
        return 3;
    }
}
