package com.legacyminecraft.poseidon.nbt;

import net.minecraft.server.NBTBase;
import net.minecraft.server.NBTTagByte;
import net.minecraft.server.NBTTagByteArray;
import net.minecraft.server.NBTTagCompound;
import net.minecraft.server.NBTTagDouble;
import net.minecraft.server.NBTTagEnd;
import net.minecraft.server.NBTTagFloat;
import net.minecraft.server.NBTTagInt;
import net.minecraft.server.NBTTagList;
import net.minecraft.server.NBTTagLong;
import net.minecraft.server.NBTTagShort;
import net.minecraft.server.NBTTagString;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Canonical registry and codec flow for legacy NBT tag type resolution.
 */
public final class NbtTypeRegistry {
    private static final NbtTypeRegistry INSTANCE = new NbtTypeRegistry();

    private NbtTypeRegistry() {
    }

    public static NbtTypeRegistry getInstance() {
        return INSTANCE;
    }

    public NBTBase readNamedTag(DataInput input) throws IOException {
        byte typeId = input.readByte();
        if (typeId == 0) {
            return new NBTTagEnd();
        }

        NBTBase tag = create(typeId);
        tag.a(input.readUTF());
        tag.readPayload(input);
        return tag;
    }

    public void writeNamedTag(NBTBase tag, DataOutput output) throws IOException {
        output.writeByte(tag.a());
        if (tag.a() == 0) {
            return;
        }

        output.writeUTF(tag.b());
        tag.writePayload(output);
    }

    public NBTBase create(byte typeId) {
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

    public String typeName(byte typeId) {
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
}
