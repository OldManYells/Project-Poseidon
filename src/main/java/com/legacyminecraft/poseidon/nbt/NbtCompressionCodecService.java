package com.legacyminecraft.poseidon.nbt;


import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @deprecated Use {@link NbtCompressionCodec}. Kept as a binary-compatible forwarding shim.
 */
@Deprecated
public final class NbtCompressionCodecService {
    private static final NbtCompressionCodecService INSTANCE = new NbtCompressionCodecService();
    private final NbtCompressionCodec codec = NbtCompressionCodec.getInstance();

    private NbtCompressionCodecService() {
    }

    public static NbtCompressionCodecService getInstance() {
        return INSTANCE;
    }

    public NBTTagCompound readCompressed(InputStream inputstream) throws IOException {
        return codec.readCompressed(inputstream);
    }

    public void writeCompressed(NBTTagCompound nbttagcompound, OutputStream outputstream) throws IOException {
        codec.writeCompressed(nbttagcompound, outputstream);
    }

    public NBTTagCompound readRootCompound(DataInput datainput) throws IOException {
        return codec.readRootCompound(datainput);
    }

    public void writeRootCompound(NBTTagCompound nbttagcompound, DataOutput dataoutput) throws IOException {
        codec.writeRootCompound(nbttagcompound, dataoutput);
    }
}
