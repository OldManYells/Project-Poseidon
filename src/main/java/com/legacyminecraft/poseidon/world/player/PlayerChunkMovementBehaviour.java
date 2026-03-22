package com.legacyminecraft.poseidon.world.player;

/**
 * Canonical behaviour for player chunk-movement threshold and coordinate policy.
 */
public final class PlayerChunkMovementBehaviour {
    private static final PlayerChunkMovementBehaviour INSTANCE = new PlayerChunkMovementBehaviour();

    private PlayerChunkMovementBehaviour() {
    }

    public static PlayerChunkMovementBehaviour getInstance() {
        return INSTANCE;
    }

    public int chunkCoordinate(double worldCoordinate) {
        return (int) worldCoordinate >> 4;
    }

    public double squaredMovement(double previousX, double previousZ, double currentX, double currentZ) {
        double deltaX = previousX - currentX;
        double deltaZ = previousZ - currentZ;
        return deltaX * deltaX + deltaZ * deltaZ;
    }

    public boolean shouldProcessChunkMovement(double squaredMovement) {
        return squaredMovement >= 64.0D;
    }
}
