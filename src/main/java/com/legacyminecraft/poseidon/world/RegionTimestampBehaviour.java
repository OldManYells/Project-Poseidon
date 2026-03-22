package com.legacyminecraft.poseidon.world;

/**
 * Canonical timestamp behaviour for region file metadata updates.
 */
public final class RegionTimestampBehaviour {
    private static final RegionTimestampBehaviour INSTANCE = new RegionTimestampBehaviour();

    private RegionTimestampBehaviour() {
    }

    public static RegionTimestampBehaviour getInstance() {
        return INSTANCE;
    }

    public int currentUnixTimeSeconds() {
        return (int) (System.currentTimeMillis() / 1000L);
    }
}
