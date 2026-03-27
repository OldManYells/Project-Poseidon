package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local world chunk manager scaffold.
 */
public class WorldChunkManager extends com.legacyminecraft.poseidon.world.WorldChunkManager {
    public BiomeBase a(ChunkCoordIntPair chunkPosition) {
        return new BiomeBase();
    }
}
