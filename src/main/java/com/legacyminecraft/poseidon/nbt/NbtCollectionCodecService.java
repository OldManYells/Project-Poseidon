package com.legacyminecraft.poseidon.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Canonical codec routines for compound and list-style NBT containers.
 *
 * @deprecated Use {@link NbtCollectionCodec}. Kept as a binary-compatible forwarding shim.
 */
@Deprecated
public final class NbtCollectionCodecService {
    private static final NbtCollectionCodecService INSTANCE = new NbtCollectionCodecService();
    private final NbtCollectionCodec codec = NbtCollectionCodec.getInstance();

    private NbtCollectionCodecService() {
    }

    public static NbtCollectionCodecService getInstance() {
        return INSTANCE;
    }

    public void writeCompound(Map entries, DataOutput output) throws IOException {
        codec.writeCompound(entries, output);
    }

    public void readCompound(Map entries, DataInput input) throws IOException {
        codec.readCompound(entries, input);
    }

    public ListWriteResult writeList(List entries, DataOutput output) throws IOException {
        NbtCollectionCodec.ListWriteResult writeResult = codec.writeList(entries, output);
        return new ListWriteResult(writeResult.getListType());
    }

    public ListReadResult readList(DataInput input) throws IOException {
        NbtCollectionCodec.ListReadResult readResult = codec.readList(input);
        return new ListReadResult(readResult.getListType(), readResult.getEntries());
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
