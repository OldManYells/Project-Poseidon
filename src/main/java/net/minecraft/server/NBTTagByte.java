package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByte extends NBTBase {

    public byte a;
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagByte() {}

    public NBTTagByte(byte b0) {
        this.a = b0;
    }

    void a(DataOutput dataoutput) throws IOException {
        nbtPrimitiveCodec.writeByte(dataoutput, this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = nbtPrimitiveCodec.readByte(datainput);
    }

    public byte a() {
        return (byte) 1;
    }

    public String toString() {
        return "" + this.a;
    }
}
