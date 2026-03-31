package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase {

    public NBTTagEnd() {}

    protected void readTagContents(DataInput input) {}

    protected void writeTagContents(DataOutput output) {}

    public byte getTypeId() {
        return (byte) 0;
    }

    public String toString() {
        return "END";
    }
}
