package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {

    public float a;

    public NBTTagFloat() {}

    public NBTTagFloat(float value) {
        this.a = value;
    }

    public float getValue() {
        return this.a;
    }

    public void setValue(float value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeFloat(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.a = input.readFloat();
    }

    public byte getTypeId() {
        return (byte) 5;
    }

    public String toString() {
        return "" + this.a;
    }
}
