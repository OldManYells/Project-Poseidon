package com.legacyminecraft.poseidon.world;

/**
 * Canonical region-file chunk index and bounds behaviour.
 */
public final class RegionChunkIndexBehaviour {
    private static final RegionChunkIndexBehaviour INSTANCE = new RegionChunkIndexBehaviour();

    private RegionChunkIndexBehaviour() {
    }

    public static RegionChunkIndexBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isOutOfBounds(int chunkX, int chunkZ) {
        return chunkX < 0 || chunkX >= 32 || chunkZ < 0 || chunkZ >= 32;
    }

    public int toTableIndex(int chunkX, int chunkZ) {
        return chunkX + chunkZ * 32;
    }

    public int getChunkOffset(int[] offsetTable, int chunkX, int chunkZ) {
        return offsetTable[toTableIndex(chunkX, chunkZ)];
    }

    public boolean hasChunk(int[] offsetTable, int chunkX, int chunkZ) {
        return getChunkOffset(offsetTable, chunkX, chunkZ) != 0;
    }
}
