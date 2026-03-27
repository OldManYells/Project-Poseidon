package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {
    public String a = "";

    public NBTTagString() {
    }

    public NBTTagString(String value) {
        this.a = value == null ? "" : value;
    }

    @Override
    void a(DataOutput dataoutput) throws IOException {
        dataoutput.writeUTF(a);
    }

    @Override
    void a(DataInput datainput) throws IOException {
        this.a = datainput.readUTF();
    }

    @Override
    public byte a() {
        return 8;
    }
}
