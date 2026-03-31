package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase {

    public int a;

    public NBTTagInt() {}

    public NBTTagInt(int value) {
        this.a = value;
    }

    public int getValue() {
        return this.a;
    }

    public void setValue(int value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeInt(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.a = input.readInt();
    }

    public byte getTypeId() {
        return (byte) 3;
    }

    public String toString() {
        return "" + this.a;
    }
}
