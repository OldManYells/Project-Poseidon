package com.legacyminecraft.poseidon.nbt;


import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * Canonical compression codec for legacy NBT root payloads.
 */
public final class NbtCompressionCodec {
    private static final NbtCompressionCodec INSTANCE = new NbtCompressionCodec();

    private NbtCompressionCodec() {
    }

    public static NbtCompressionCodec getInstance() {
        return INSTANCE;
    }

    public NBTTagCompound readCompressed(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(new GZIPInputStream(inputStream));
        try {
            return this.readRootCompound(dataInputStream);
        } finally {
            dataInputStream.close();
        }
    }

    public void writeCompressed(NBTTagCompound rootCompound, OutputStream outputStream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(new GZIPOutputStream(outputStream));
        try {
            this.writeRootCompound(rootCompound, dataOutputStream);
        } finally {
            dataOutputStream.close();
        }
    }

    public NBTTagCompound readRootCompound(DataInput dataInput) throws IOException {
        NBTBase rootTag = NBTBase.b(dataInput);

        if (rootTag instanceof NBTTagCompound) {
            return (NBTTagCompound) rootTag;
        }

        throw new IOException("Root tag must be a named compound tag");
    }

    public void writeRootCompound(NBTTagCompound rootCompound, DataOutput dataOutput) throws IOException {
        NBTBase.a(rootCompound, dataOutput);
    }
}
