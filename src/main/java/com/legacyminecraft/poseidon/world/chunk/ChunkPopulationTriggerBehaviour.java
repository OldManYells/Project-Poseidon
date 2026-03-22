package com.legacyminecraft.poseidon.world.chunk;

import net.minecraft.server.Chunk;

/**
 * Canonical behaviour for deciding when neighboring chunks should be populated.
 */
public final class ChunkPopulationTriggerBehaviour {
    private static final ChunkPopulationTriggerBehaviour INSTANCE = new ChunkPopulationTriggerBehaviour();

    private ChunkPopulationTriggerBehaviour() {
    }

    public static ChunkPopulationTriggerBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldPopulateCurrentChunk(Chunk chunk, boolean hasEastSouthEastSouth) {
        return !chunk.done && hasEastSouthEastSouth;
    }

    public boolean shouldPopulateWestNeighbor(boolean hasWest, boolean westDone, boolean hasWestSouth, boolean hasSouth) {
        return hasWest && !westDone && hasWestSouth && hasSouth;
    }

    public boolean shouldPopulateNorthNeighbor(boolean hasNorth, boolean northDone, boolean hasNorthEast, boolean hasEast) {
        return hasNorth && !northDone && hasNorthEast && hasEast;
    }

    public boolean shouldPopulateNorthWestNeighbor(boolean hasNorthWest, boolean northWestDone, boolean hasNorth, boolean hasWest) {
        return hasNorthWest && !northWestDone && hasNorth && hasWest;
    }
}
