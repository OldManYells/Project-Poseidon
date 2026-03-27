package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByteArray extends NBTBase {
    public byte[] a = new byte[0];

    public NBTTagByteArray() {
    }

    public NBTTagByteArray(byte[] value) {
        this.a = value == null ? new byte[0] : value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeInt(a.length);
        dataoutput.write(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        int length = datainput.readInt();
        if (length < 0) {
            length = 0;
        }
        this.a = new byte[length];
        datainput.readFully(this.a);
    }

    @Override
    public byte a() {
        return 7;
    }
}
