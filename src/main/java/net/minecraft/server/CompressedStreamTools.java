package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtCompressionCodecService;

import java.io.*;

public class CompressedStreamTools {
    private static final NbtCompressionCodecService NBT_COMPRESSION_CODEC_SERVICE = NbtCompressionCodecService.getInstance();

    public CompressedStreamTools() {}

    public static NBTTagCompound a(InputStream inputstream) throws IOException {
        return NBT_COMPRESSION_CODEC_SERVICE.readCompressed(inputstream);
    }

    public static void a(NBTTagCompound nbttagcompound, OutputStream outputstream) throws IOException {
        NBT_COMPRESSION_CODEC_SERVICE.writeCompressed(nbttagcompound, outputstream);
    }

    public static NBTTagCompound a(DataInput datainput) throws IOException {
        return NBT_COMPRESSION_CODEC_SERVICE.readRootCompound(datainput);
    }

    public static void a(NBTTagCompound nbttagcompound, DataOutput dataoutput) throws IOException {
        NBT_COMPRESSION_CODEC_SERVICE.writeRootCompound(nbttagcompound, dataoutput);
    }
}
