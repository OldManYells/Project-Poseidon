package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase {
    public long a;

    public NBTTagLong() {
    }

    public NBTTagLong(long value) {
        this.a = value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeLong(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        this.a = datainput.readLong();
    }

    @Override
    public byte a() {
        return 4;
    }
}
