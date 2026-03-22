package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtTypeRegistryService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public abstract class NBTBase {

    private String a = null;
    private static final NbtTypeRegistryService nbtTypeRegistryService = NbtTypeRegistryService.getInstance();

    public NBTBase() {}

    abstract void a(DataOutput dataoutput) throws IOException;

    abstract void a(DataInput datainput) throws IOException;

    public abstract byte a();

    public String b() {
        return this.a == null ? "" : this.a;
    }

    public NBTBase a(String s) {
        this.a = s;
        return this;
    }

    public final void writePayload(DataOutput dataoutput) throws IOException {
        this.a(dataoutput);
    }

    public final void readPayload(DataInput datainput) throws IOException {
        this.a(datainput);
    }

    public static NBTBase b(DataInput datainput) throws IOException {
        return nbtTypeRegistryService.readNamedTag(datainput);
    }

    public static void a(NBTBase nbtbase, DataOutput dataoutput) throws IOException {
        nbtTypeRegistryService.writeNamedTag(nbtbase, dataoutput);
    }

    public static NBTBase a(byte b0) {
        return nbtTypeRegistryService.create(b0);
    }

    public static String b(byte b0) {
        return nbtTypeRegistryService.typeName(b0);
    }
}
