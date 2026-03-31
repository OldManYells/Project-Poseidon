package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase {

    public short a;

    public NBTTagShort() {}

    public NBTTagShort(short value) {
        this.a = value;
    }

    public short getValue() {
        return this.a;
    }

    public void setValue(short value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeShort(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.a = input.readShort();
    }

    public byte getTypeId() {
        return (byte) 2;
    }

    public String toString() {
        return "" + this.a;
    }
}
