package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Canonical NBT list scaffold.
 */
public class NBTTagList extends NBTBase {
    private final List<NBTBase> entries = new ArrayList<NBTBase>();

    @Override
    void a(DataOutput dataoutput) throws IOException {
    }

    @Override
    void a(DataInput datainput) throws IOException {
    }

    @Override
    public byte a() {
        return 9;
    }

    public int size() {
        return entries.size();
    }

    public int c() {
        return entries.size();
    }

    public void a(NBTBase tag) {
        entries.add(tag);
    }

    public NBTBase a(int index) {
        return entries.get(index);
    }
}
