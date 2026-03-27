package com.legacyminecraft.compat.bukkit;

/**
 * Canonical behavior for chunk snapshot metadata access.
 */
public final class ChunkSnapshotMetadataAccessBehaviour {
    private static final ChunkSnapshotMetadataAccessBehaviour INSTANCE = new ChunkSnapshotMetadataAccessBehaviour();

    private ChunkSnapshotMetadataAccessBehaviour() {
    }

    public static ChunkSnapshotMetadataAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public int getX(int x) {
        return x;
    }

    public int getZ(int z) {
        return z;
    }

    public String getWorldName(String worldName) {
        return worldName;
    }

    public long getCaptureFullTime(long captureFullTime) {
        return captureFullTime;
    }
}
