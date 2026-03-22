package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {

    public double a;
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagDouble() {}

    public NBTTagDouble(double d0) {
        this.a = d0;
    }

    void a(DataOutput dataoutput) throws IOException {
        nbtPrimitiveCodec.writeDouble(dataoutput, this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = nbtPrimitiveCodec.readDouble(datainput);
    }

    public byte a() {
        return (byte) 6;
    }

    public String toString() {
        return "" + this.a;
    }
}
