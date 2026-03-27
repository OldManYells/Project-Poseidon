package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for chunk snapshot block/light/height lookups.
 */
public final class ChunkSnapshotBlockLookupBehaviour {
    private static final ChunkSnapshotBlockLookupBehaviour INSTANCE = new ChunkSnapshotBlockLookupBehaviour();

    private ChunkSnapshotBlockLookupBehaviour() {
    }

    public static ChunkSnapshotBlockLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public int getBlockTypeId(ChunkSnapshotDataAccessBehaviour dataAccessBehaviour, byte[] chunkBuffer, int x, int y, int z) {
        return dataAccessBehaviour.readBlockTypeId(chunkBuffer, x, y, z);
    }

    public int getBlockData(ChunkSnapshotDataAccessBehaviour dataAccessBehaviour, byte[] chunkBuffer, int blockDataOffset, int x, int y, int z) {
        return dataAccessBehaviour.readBlockData(chunkBuffer, blockDataOffset, x, y, z);
    }

    public int getSkyLight(ChunkSnapshotDataAccessBehaviour dataAccessBehaviour, byte[] chunkBuffer, int skyLightOffset, int x, int y, int z) {
        return dataAccessBehaviour.readSkyLight(chunkBuffer, skyLightOffset, x, y, z);
    }

    public int getEmittedLight(ChunkSnapshotDataAccessBehaviour dataAccessBehaviour, byte[] chunkBuffer, int emittedLightOffset, int x, int y, int z) {
        return dataAccessBehaviour.readEmittedLight(chunkBuffer, emittedLightOffset, x, y, z);
    }

    public int getHighestBlockY(ChunkSnapshotDataAccessBehaviour dataAccessBehaviour, byte[] heightMap, int x, int z) {
        return dataAccessBehaviour.readHighestBlockY(heightMap, x, z);
    }
}
