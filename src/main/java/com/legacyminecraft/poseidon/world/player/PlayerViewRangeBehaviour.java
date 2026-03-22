package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical behaviour for player chunk view-range policy.
 */
public final class PlayerViewRangeBehaviour {
    private static final PlayerViewRangeBehaviour INSTANCE = new PlayerViewRangeBehaviour();

    private PlayerViewRangeBehaviour() {
    }

    public static PlayerViewRangeBehaviour getInstance() {
        return INSTANCE;
    }

    public void validateViewRadius(int viewRadius) {
        if (viewRadius > 15) {
            throw new IllegalArgumentException("Too big view radius!");
        }

        if (viewRadius < 3) {
            throw new IllegalArgumentException("Too small view radius!");
        }
    }

    public boolean isWithinViewRange(int chunkX, int chunkZ, int centerChunkX, int centerChunkZ, int viewRadius) {
        int deltaX = chunkX - centerChunkX;
        int deltaZ = chunkZ - centerChunkZ;
        return deltaX >= -viewRadius && deltaX <= viewRadius && deltaZ >= -viewRadius && deltaZ <= viewRadius;
    }

    public int furthestViewableBlock(int viewRadius) {
        return viewRadius * 16 - 16;
    }
}
