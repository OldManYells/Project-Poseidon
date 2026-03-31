package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTTagList extends NBTBase {

    private List tagList = new ArrayList();
    private byte containedType;

    public NBTTagList() {}

    protected void writeTagContents(DataOutput output) throws IOException {
        if (this.tagList.size() > 0) {
            this.containedType = ((NBTBase) this.tagList.get(0)).getTypeId();
        } else {
            this.containedType = 1;
        }

        output.writeByte(this.containedType);
        output.writeInt(this.tagList.size());

        for (int index = 0; index < this.tagList.size(); ++index) {
            ((NBTBase) this.tagList.get(index)).writeTagContents(output);
        }
    }

    protected void readTagContents(DataInput input) throws IOException {
        this.containedType = input.readByte();
        int size = input.readInt();
        this.tagList = new ArrayList();

        for (int index = 0; index < size; ++index) {
            NBTBase tag = NBTBase.createTagOfType(this.containedType);
            tag.readTagContents(input);
            this.tagList.add(tag);
        }
    }

    public byte getTypeId() {
        return (byte) 9;
    }

    public void add(NBTBase tag) {
        this.containedType = tag.getTypeId();
        this.tagList.add(tag);
    }

    public NBTBase get(int index) {
        return (NBTBase) this.tagList.get(index);
    }

    public int size() {
        return this.tagList.size();
    }

    public String toString() {
        return "" + this.tagList.size() + " entries of type " + NBTBase.getTagName(this.containedType);
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    @Deprecated
    public void a(NBTBase tag) {
        this.add(tag);
    }

    @Deprecated
    public NBTBase a(int index) {
        return this.get(index);
    }

    @Deprecated
    public int c() {
        return this.size();
    }
}
