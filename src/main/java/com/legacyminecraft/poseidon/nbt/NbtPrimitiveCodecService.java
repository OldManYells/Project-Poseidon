package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Canonical primitive payload codec for legacy NBT scalar tags.
 *
 * @deprecated Use {@link NbtPrimitiveCodec}. Kept as a binary-compatible forwarding shim.
 */
@Deprecated
public final class NbtPrimitiveCodecService {
    private static final NbtPrimitiveCodecService INSTANCE = new NbtPrimitiveCodecService();
    private final NbtPrimitiveCodec codec = NbtPrimitiveCodec.getInstance();

    private NbtPrimitiveCodecService() {
    }

    public static NbtPrimitiveCodecService getInstance() {
        return INSTANCE;
    }

    public byte readByte(DataInput input) throws IOException {
        return codec.readByte(input);
    }

    public void writeByte(DataOutput output, byte value) throws IOException {
        codec.writeByte(output, value);
    }

    public short readShort(DataInput input) throws IOException {
        return codec.readShort(input);
    }

    public void writeShort(DataOutput output, short value) throws IOException {
        codec.writeShort(output, value);
    }

    public int readInt(DataInput input) throws IOException {
        return codec.readInt(input);
    }

    public void writeInt(DataOutput output, int value) throws IOException {
        codec.writeInt(output, value);
    }

    public long readLong(DataInput input) throws IOException {
        return codec.readLong(input);
    }

    public void writeLong(DataOutput output, long value) throws IOException {
        codec.writeLong(output, value);
    }

    public float readFloat(DataInput input) throws IOException {
        return codec.readFloat(input);
    }

    public void writeFloat(DataOutput output, float value) throws IOException {
        codec.writeFloat(output, value);
    }

    public double readDouble(DataInput input) throws IOException {
        return codec.readDouble(input);
    }

    public void writeDouble(DataOutput output, double value) throws IOException {
        codec.writeDouble(output, value);
    }

    public String readString(DataInput input) throws IOException {
        return codec.readString(input);
    }

    public void writeString(DataOutput output, String value) throws IOException {
        codec.writeString(output, value);
    }

    public byte[] readByteArray(DataInput input) throws IOException {
        return codec.readByteArray(input);
    }

    public void writeByteArray(DataOutput output, byte[] payload) throws IOException {
        codec.writeByteArray(output, payload);
    }

    public void readEnd(DataInput input) {
        codec.readEnd(input);
    }

    public void writeEnd(DataOutput output) {
        codec.writeEnd(output);
    }

    public byte endTypeId() {
        return codec.endTypeId();
    }
}
