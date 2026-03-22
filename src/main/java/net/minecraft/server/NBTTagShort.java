package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase {

    public short a;
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagShort() {}

    public NBTTagShort(short short1) {
        this.a = short1;
    }

    void a(DataOutput dataoutput) throws IOException {
        nbtPrimitiveCodec.writeShort(dataoutput, this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = nbtPrimitiveCodec.readShort(datainput);
    }

    public byte a() {
        return (byte) 2;
    }

    public String toString() {
        return "" + this.a;
    }
}
