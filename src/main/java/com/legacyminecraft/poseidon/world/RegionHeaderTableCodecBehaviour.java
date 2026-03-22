package com.legacyminecraft.poseidon.world;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Canonical codec behaviour for region header tables (offsets/timestamps).
 */
public final class RegionHeaderTableCodecBehaviour {
    private static final RegionHeaderTableCodecBehaviour INSTANCE = new RegionHeaderTableCodecBehaviour();

    private RegionHeaderTableCodecBehaviour() {
    }

    public static RegionHeaderTableCodecBehaviour getInstance() {
        return INSTANCE;
    }

    public void readTable(RandomAccessFile regionHandle, int[] destination) throws IOException {
        for (int index = 0; index < 1024; ++index) {
            destination[index] = regionHandle.readInt();
        }
    }

    public void writeTableEntry(RandomAccessFile regionHandle, long tableBaseOffsetBytes, int tableIndex, int value) throws IOException {
        regionHandle.seek(tableBaseOffsetBytes + (long) tableIndex * 4L);
        regionHandle.writeInt(value);
    }
}
