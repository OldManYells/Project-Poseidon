package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {

    public String a;
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagString() {}

    public NBTTagString(String s) {
        this.a = s;
        if (s == null) {
            throw new IllegalArgumentException("Empty string not allowed");
        }
    }

    void a(DataOutput dataoutput) throws IOException {
        nbtPrimitiveCodec.writeString(dataoutput, this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = nbtPrimitiveCodec.readString(datainput);
    }

    public byte a() {
        return (byte) 8;
    }

    public String toString() {
        return "" + this.a;
    }
}
