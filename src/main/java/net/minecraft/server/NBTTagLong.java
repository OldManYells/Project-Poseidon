package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase {

    public long a;
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagLong() {}

    public NBTTagLong(long i) {
        this.a = i;
    }

    void a(DataOutput dataoutput) throws IOException {
        nbtPrimitiveCodec.writeLong(dataoutput, this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = nbtPrimitiveCodec.readLong(datainput);
    }

    public byte a() {
        return (byte) 4;
    }

    public String toString() {
        return "" + this.a;
    }
}
