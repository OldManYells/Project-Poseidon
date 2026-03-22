package com.legacyminecraft.poseidon.world;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Canonical behaviour for writing compressed chunk payloads into region sectors.
 */
public final class RegionSectorWriteBehaviour {
    private static final RegionSectorWriteBehaviour INSTANCE = new RegionSectorWriteBehaviour();

    private RegionSectorWriteBehaviour() {
    }

    public static RegionSectorWriteBehaviour getInstance() {
        return INSTANCE;
    }

    public void writeCompressedChunk(RandomAccessFile regionHandle, int sectorOffset, byte[] payload, int payloadSize) throws IOException {
        regionHandle.seek((long) (sectorOffset * 4096));
        regionHandle.writeInt(payloadSize + 1);
        regionHandle.writeByte(2);
        regionHandle.write(payload, 0, payloadSize);
    }
}
