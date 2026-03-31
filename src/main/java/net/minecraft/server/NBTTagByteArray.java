package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByteArray extends NBTBase {

    public byte[] a;

    public NBTTagByteArray() {}

    public NBTTagByteArray(byte[] value) {
        this.a = value;
    }

    public byte[] getValue() {
        return this.a;
    }

    public void setValue(byte[] value) {
        this.a = value;
    }

    protected void writeTagContents(DataOutput output) throws IOException {
        output.writeInt(this.a.length);
        output.write(this.a);
    }

    protected void readTagContents(DataInput input) throws IOException {
        int length = input.readInt();
        this.a = new byte[length];
        input.readFully(this.a);
    }

    public byte getTypeId() {
        return (byte) 7;
    }

    public String toString() {
        return "[" + this.a.length + " bytes]";
    }
}
