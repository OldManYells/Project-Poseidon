package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtPrimitiveCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagByteArray extends NBTBase {

    public byte[] a;
    private final NbtPrimitiveCodecService nbtPrimitiveCodec = NbtPrimitiveCodecService.getInstance();

    public NBTTagByteArray() {}

    public NBTTagByteArray(byte[] abyte) {
        this.a = abyte;
    }

    void a(DataOutput dataoutput) throws IOException {
        nbtPrimitiveCodec.writeByteArray(dataoutput, this.a);
    }

    void a(DataInput datainput) throws IOException {
        this.a = nbtPrimitiveCodec.readByteArray(datainput);
    }

    public byte a() {
        return (byte) 7;
    }

    public String toString() {
        return "[" + this.a.length + " bytes]";
    }
}
