package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical behaviour for dirty-block flush policy and geometry calculations.
 */
public final class PlayerChunkDirtyFlushBehaviour {
    private static final PlayerChunkDirtyFlushBehaviour INSTANCE = new PlayerChunkDirtyFlushBehaviour();

    private PlayerChunkDirtyFlushBehaviour() {
    }

    public static PlayerChunkDirtyFlushBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isSingleBlockUpdate(int dirtyCount) {
        return dirtyCount == 1;
    }

    public boolean isFullChunkSectionUpdate(int dirtyCount) {
        return dirtyCount == 10;
    }

    public int worldCoordinate(int chunkCoordinate, int localCoordinate) {
        return chunkCoordinate * 16 + localCoordinate;
    }

    public int alignSectionMinY(int minY) {
        return minY / 2 * 2;
    }

    public int alignSectionMaxY(int maxY) {
        return (maxY / 2 + 1) * 2;
    }

    public int sectionWidth(int minLocalX, int maxLocalX) {
        return maxLocalX - minLocalX + 1;
    }

    public int sectionHeight(int minY, int maxY) {
        return maxY - minY + 2;
    }

    public int sectionDepth(int minLocalZ, int maxLocalZ) {
        return maxLocalZ - minLocalZ + 1;
    }
}
