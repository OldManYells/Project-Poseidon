package net.minecraft.server;

import com.legacyminecraft.poseidon.nbt.NbtCollectionCodecService;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NBTTagList extends NBTBase {

    private List a = new ArrayList();
    private byte b;
    private final NbtCollectionCodecService nbtCollectionCodec = NbtCollectionCodecService.getInstance();

    public NBTTagList() {}

    void a(DataOutput dataoutput) throws IOException {
        NbtCollectionCodecService.ListWriteResult writeResult = nbtCollectionCodec.writeList(this.a, dataoutput);
        this.b = writeResult.getListType();
    }

    void a(DataInput datainput) throws IOException {
        NbtCollectionCodecService.ListReadResult readResult = nbtCollectionCodec.readList(datainput);
        this.b = readResult.getListType();
        this.a = readResult.getEntries();
    }

    public byte a() {
        return (byte) 9;
    }

    public String toString() {
        return "" + this.a.size() + " entries of type " + NBTBase.b(this.b);
    }

    public void a(NBTBase nbtbase) {
        this.b = nbtbase.a();
        this.a.add(nbtbase);
    }

    public NBTBase a(int i) {
        return (NBTBase) this.a.get(i);
    }

    public int c() {
        return this.a.size();
    }
}
