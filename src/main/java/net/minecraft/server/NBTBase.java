package net.minecraft.server;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {

    private String name = null;

    public NBTBase() {}

    protected abstract void writeTagContents(DataOutput output) throws IOException;

    protected abstract void readTagContents(DataInput input) throws IOException;

    public abstract byte getTypeId();

    public String getName() {
        return this.name == null ? "" : this.name;
    }

    public NBTBase setName(String name) {
        this.name = name;
        return this;
    }

    public static NBTBase readNamedTag(DataInput input) throws IOException {
        byte typeId = input.readByte();

        if (typeId == 0) {
            return new NBTTagEnd();
        }

        NBTBase tag = createTagOfType(typeId);
        tag.name = input.readUTF();
        tag.readTagContents(input);
        return tag;
    }

    public static void writeNamedTag(NBTBase tag, DataOutput output) throws IOException {
        output.writeByte(tag.getTypeId());
        if (tag.getTypeId() != 0) {
            output.writeUTF(tag.getName());
            tag.writeTagContents(output);
        }
    }

    public static NBTBase createTagOfType(byte typeId) {
        switch (typeId) {
        case 0:
            return new NBTTagEnd();
        case 1:
            return new NBTTagByte();
        case 2:
            return new NBTTagShort();
        case 3:
            return new NBTTagInt();
        case 4:
            return new NBTTagLong();
        case 5:
            return new NBTTagFloat();
        case 6:
            return new NBTTagDouble();
        case 7:
            return new NBTTagByteArray();
        case 8:
            return new NBTTagString();
        case 9:
            return new NBTTagList();
        case 10:
            return new NBTTagCompound();
        default:
            return null;
        }
    }

    public static String getTagName(byte typeId) {
        switch (typeId) {
        case 0:
            return "TAG_End";
        case 1:
            return "TAG_Byte";
        case 2:
            return "TAG_Short";
        case 3:
            return "TAG_Int";
        case 4:
            return "TAG_Long";
        case 5:
            return "TAG_Float";
        case 6:
            return "TAG_Double";
        case 7:
            return "TAG_Byte_Array";
        case 8:
            return "TAG_String";
        case 9:
            return "TAG_List";
        case 10:
            return "TAG_Compound";
        default:
            return "UNKNOWN";
        }
    }

    // ---------------------------------------------------------------------
    // Compatibility bridge methods for old obfuscated call sites
    // ---------------------------------------------------------------------

    @Deprecated
    void a(DataOutput output) throws IOException {
        this.writeTagContents(output);
    }

    @Deprecated
    void a(DataInput input) throws IOException {
        this.readTagContents(input);
    }

    @Deprecated
    public byte a() {
        return this.getTypeId();
    }

    @Deprecated
    public String b() {
        return this.getName();
    }

    @Deprecated
    public NBTBase a(String name) {
        return this.setName(name);
    }

    @Deprecated
    public static NBTBase b(DataInput input) throws IOException {
        return readNamedTag(input);
    }

    @Deprecated
    public static void a(NBTBase tag, DataOutput output) throws IOException {
        writeNamedTag(tag, output);
    }

    @Deprecated
    public static NBTBase a(byte typeId) {
        return createTagOfType(typeId);
    }

    @Deprecated
    public static String b(byte typeId) {
        return getTagName(typeId);
    }
}
