package com.legacyminecraft.poseidon.nbt;

import net.minecraft.server.NBTBase;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Canonical codec routines for compound and list-style NBT containers.
 */
public final class NbtCollectionCodec {
    private static final NbtCollectionCodec INSTANCE = new NbtCollectionCodec();
    private static final byte DEFAULT_EMPTY_LIST_TYPE = 1;

    private NbtCollectionCodec() {
    }

    public static NbtCollectionCodec getInstance() {
        return INSTANCE;
    }

    public void writeCompound(Map entries, DataOutput output) throws IOException {
        Iterator iterator = entries.values().iterator();
        while (iterator.hasNext()) {
            NBTBase tag = (NBTBase) iterator.next();
            NBTBase.a(tag, output);
        }
        output.writeByte(0);
    }

    public void readCompound(Map entries, DataInput input) throws IOException {
        entries.clear();

        NBTBase nextTag;
        while ((nextTag = NBTBase.b(input)).a() != 0) {
            entries.put(nextTag.b(), nextTag);
        }
    }

    public ListWriteResult writeList(List entries, DataOutput output) throws IOException {
        byte listType = entries.size() > 0 ? ((NBTBase) entries.get(0)).a() : DEFAULT_EMPTY_LIST_TYPE;
        output.writeByte(listType);
        output.writeInt(entries.size());
        for (int i = 0; i < entries.size(); ++i) {
            ((NBTBase) entries.get(i)).writePayload(output);
        }
        return new ListWriteResult(listType);
    }

    public ListReadResult readList(DataInput input) throws IOException {
        byte listType = input.readByte();
        int size = input.readInt();
        List entries = new ArrayList();
        for (int i = 0; i < size; ++i) {
            NBTBase tag = NBTBase.a(listType);
            tag.readPayload(input);
            entries.add(tag);
        }
        return new ListReadResult(listType, entries);
    }

    public static final class ListWriteResult {
        private final byte listType;

        private ListWriteResult(byte listType) {
            this.listType = listType;
        }

        public byte getListType() {
            return listType;
        }
    }

    public static final class ListReadResult {
        private final byte listType;
        private final List entries;

        private ListReadResult(byte listType, List entries) {
            this.listType = listType;
            this.entries = entries;
        }

        public byte getListType() {
            return listType;
        }

        public List getEntries() {
            return entries;
        }
    }
}
