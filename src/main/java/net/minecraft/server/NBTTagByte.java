package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase {

    public byte a;

    public NBTTagByte() {}

    public NBTTagByte(byte value) {
        this.a = value;
    }

    public byte getValue() {
        return this.a;
    }

    public void setValue(byte value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeByte(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.a = input.readByte();
    }

    public byte getTypeId() {
        return (byte) 1;
    }

    public String toString() {
        return "" + this.a;
    }
}
