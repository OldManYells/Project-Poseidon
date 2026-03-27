package com.legacyminecraft.poseidon.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity-local NBT list scaffold.
 */
public class NBTTagList extends NBTBase {
    private final List<NBTBase> entries = new ArrayList<NBTBase>();

    public void a(NBTBase tag) {
        entries.add(tag);
    }

    public NBTBase a(int index) {
        return entries.get(index);
    }

    public int c() {
        return entries.size();
    }
}
