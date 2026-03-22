package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical coordinate conversion behaviour for player chunk management.
 */
public final class PlayerChunkCoordinateBehaviour {
    private static final PlayerChunkCoordinateBehaviour INSTANCE = new PlayerChunkCoordinateBehaviour();

    private PlayerChunkCoordinateBehaviour() {
    }

    public static PlayerChunkCoordinateBehaviour getInstance() {
        return INSTANCE;
    }

    public int chunkFromBlock(int blockCoordinate) {
        return blockCoordinate >> 4;
    }

    public int localBlockInChunk(int blockCoordinate) {
        return blockCoordinate & 15;
    }

    public int chunkFromWorldPosition(double worldCoordinate) {
        return (int) worldCoordinate >> 4;
    }
}
