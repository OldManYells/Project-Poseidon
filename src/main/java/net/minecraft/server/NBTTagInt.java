package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase {

    public int a;
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagInt() {}

    public NBTTagInt(int i) {
        this.a = i;
    }

    void a(DataOutput dataoutput) throws IOException {
        nbtPrimitiveCodec.writeInt(dataoutput, this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = nbtPrimitiveCodec.readInt(datainput);
    }

    public byte a() {
        return (byte) 3;
    }

    public String toString() {
        return "" + this.a;
    }
}
