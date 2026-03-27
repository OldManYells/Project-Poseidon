package com.legacyminecraft.poseidon.world;


public final class RegionChunkBufferBehaviour {
    private static final RegionChunkBufferBehaviour INSTANCE = new RegionChunkBufferBehaviour();

    private RegionChunkBufferBehaviour() {
    }

    public static RegionChunkBufferBehaviour getInstance() {
        return INSTANCE;
    }

    public void flushToRegion(RegionFile regionFile, int chunkX, int chunkZ, byte[] data, int size) {
        regionFile.poseidonWriteChunkData(chunkX, chunkZ, data, size);
    }
}
