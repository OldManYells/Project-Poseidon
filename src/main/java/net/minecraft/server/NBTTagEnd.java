package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;

public class NBTTagEnd extends NBTBase {
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagEnd() {}

    void a(DataInput datainput) {
        nbtPrimitiveCodec.readEnd(datainput);
    }

    void a(DataOutput dataoutput) {
        nbtPrimitiveCodec.writeEnd(dataoutput);
    }

    public byte a() {
        return nbtPrimitiveCodec.endTypeId();
    }

    public String toString() {
        return "END";
    }
}
