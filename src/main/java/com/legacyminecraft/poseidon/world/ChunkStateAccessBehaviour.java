package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for chunk state access helpers (coordinates, height-map, metadata).
 */
public final class ChunkStateAccessBehaviour {
    private static final ChunkStateAccessBehaviour INSTANCE = new ChunkStateAccessBehaviour();

    private ChunkStateAccessBehaviour() {
    }

    public static ChunkStateAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isChunkCoordinates(int x, int z, int chunkX, int chunkZ) {
        return x == chunkX && z == chunkZ;
    }

    public int getHeightAt(byte[] heightMap, int x, int z) {
        return heightMap[z << 4 | x] & 255;
    }

    public boolean isAtOrAboveHeight(byte[] heightMap, int x, int y, int z) {
        return y >= getHeightAt(heightMap, x, z);
    }

    public int getMetadata(NibbleArray metadata, int x, int y, int z) {
        return metadata.a(x, y, z);
    }

    public void setMetadata(NibbleArray metadata, int x, int y, int z, int value) {
        metadata.a(x, y, z, value);
    }
}

