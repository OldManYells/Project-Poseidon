package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {

    public double a;

    public NBTTagDouble() {}

    public NBTTagDouble(double value) {
        this.a = value;
    }

    public double getValue() {
        return this.a;
    }

    public void setValue(double value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeDouble(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.a = input.readDouble();
    }

    public byte getTypeId() {
        return (byte) 6;
    }

    public String toString() {
        return "" + this.a;
    }
}
