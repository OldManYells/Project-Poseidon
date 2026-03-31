package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase {

    public long a;

    public NBTTagLong() {}

    public NBTTagLong(long value) {
        this.a = value;
    }

    public long getValue() {
        return this.a;
    }

    public void setValue(long value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeLong(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.a = input.readLong();
    }

    public byte getTypeId() {
        return (byte) 4;
    }

    public String toString() {
        return "" + this.a;
    }
}
