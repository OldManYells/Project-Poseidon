package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

    public String a;

    public NBTTagString() {}

    public NBTTagString(String value) {
        this.a = value;
        if (value == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    public String getValue() {
        return this.a;
    }

    public void setValue(String value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeUTF(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.a = input.readUTF();
    }

    public byte getTypeId() {
        return (byte) 8;
    }

    public String toString() {
        return "" + this.a;
    }
}
