package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Canonical primitive payload codec for legacy NBT scalar tags.
 */
public final class NbtPrimitiveCodec {
    private static final NbtPrimitiveCodec INSTANCE = new NbtPrimitiveCodec();

    private NbtPrimitiveCodec() {
    }

    public static NbtPrimitiveCodec getInstance() {
        return INSTANCE;
    }

    public byte readByte(DataInput input) throws IOException {
        return input.readByte();
    }

    public void writeByte(DataOutput output, byte value) throws IOException {
        output.writeByte(value);
    }

    public short readShort(DataInput input) throws IOException {
        return input.readShort();
    }

    public void writeShort(DataOutput output, short value) throws IOException {
        output.writeShort(value);
    }

    public int readInt(DataInput input) throws IOException {
        return input.readInt();
    }

    public void writeInt(DataOutput output, int value) throws IOException {
        output.writeInt(value);
    }

    public long readLong(DataInput input) throws IOException {
        return input.readLong();
    }

    public void writeLong(DataOutput output, long value) throws IOException {
        output.writeLong(value);
    }

    public float readFloat(DataInput input) throws IOException {
        return input.readFloat();
    }

    public void writeFloat(DataOutput output, float value) throws IOException {
        output.writeFloat(value);
    }

    public double readDouble(DataInput input) throws IOException {
        return input.readDouble();
    }

    public void writeDouble(DataOutput output, double value) throws IOException {
        output.writeDouble(value);
    }

    public String readString(DataInput input) throws IOException {
        return input.readUTF();
    }

    public void writeString(DataOutput output, String value) throws IOException {
        output.writeUTF(value);
    }

    public byte[] readByteArray(DataInput input) throws IOException {
        int length = input.readInt();
        byte[] payload = new byte[length];
        input.readFully(payload);
        return payload;
    }

    public void writeByteArray(DataOutput output, byte[] payload) throws IOException {
        output.writeInt(payload.length);
        output.write(payload);
    }

    public void readEnd(DataInput input) {
    }

    public void writeEnd(DataOutput output) {
    }

    public byte endTypeId() {
        return 0;
    }
}
