package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftChunkSnapshot packed-buffer and column data access.
 */
public final class ChunkSnapshotDataAccessBehaviour {
    private static final ChunkSnapshotDataAccessBehaviour INSTANCE = new ChunkSnapshotDataAccessBehaviour();

    private ChunkSnapshotDataAccessBehaviour() {
    }

    public static ChunkSnapshotDataAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public int readBlockTypeId(byte[] chunkData, int x, int y, int z) {
        return chunkData[blockTypeOffset(x, y, z)] & 255;
    }

    public int readBlockData(byte[] chunkData, int blockDataOffset, int x, int y, int z) {
        int nibbleByteOffset = nibbleOffset(x, y, z, blockDataOffset);
        return readNibble(chunkData, nibbleByteOffset, y);
    }

    public int readSkyLight(byte[] chunkData, int skyLightOffset, int x, int y, int z) {
        int nibbleByteOffset = nibbleOffset(x, y, z, skyLightOffset);
        return readNibble(chunkData, nibbleByteOffset, y);
    }

    public int readEmittedLight(byte[] chunkData, int blockLightOffset, int x, int y, int z) {
        int nibbleByteOffset = nibbleOffset(x, y, z, blockLightOffset);
        return readNibble(chunkData, nibbleByteOffset, y);
    }

    public int readHighestBlockY(byte[] heightMap, int x, int z) {
        return heightMap[columnOffset(x, z)] & 255;
    }

    public Biome readBiome(BiomeBase[] biomeData, int x, int z, BiomeResolver biomeResolver) {
        return biomeResolver.resolve(biomeData[columnOffset(x, z)]);
    }

    public double readClimateValue(double[] climateData, int x, int z) {
        return climateData[columnOffset(x, z)];
    }

    private int blockTypeOffset(int x, int y, int z) {
        return x << 11 | z << 7 | y;
    }

    private int nibbleOffset(int x, int y, int z, int sectionOffset) {
        return ((x << 10) | (z << 6) | (y >> 1)) + sectionOffset;
    }

    private int columnOffset(int x, int z) {
        return x << 4 | z;
    }

    private int readNibble(byte[] chunkData, int nibbleByteOffset, int y) {
        return ((y & 1) == 0) ? (chunkData[nibbleByteOffset] & 0xF) : ((chunkData[nibbleByteOffset] >> 4) & 0xF);
    }

    public interface BiomeResolver {
        Biome resolve(BiomeBase biomeBase);
    }
}

