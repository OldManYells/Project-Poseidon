package com.legacyminecraft.poseidon.world;

/**
 * Canonical behaviour for region-file write statistics counters.
 */
public final class RegionWriteStatsBehaviour {
    private static final RegionWriteStatsBehaviour INSTANCE = new RegionWriteStatsBehaviour();

    private RegionWriteStatsBehaviour() {
    }

    public static RegionWriteStatsBehaviour getInstance() {
        return INSTANCE;
    }

    public int pullAndResetBytesWritten(int currentBytesWritten) {
        return currentBytesWritten;
    }
}
