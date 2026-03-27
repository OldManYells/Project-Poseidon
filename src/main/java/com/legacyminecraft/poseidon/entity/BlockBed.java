package com.legacyminecraft.poseidon.entity;

/**
 * Entity-local bed lookup scaffold.
 */
public final class BlockBed {
    private BlockBed() {
    }

    public static ChunkCoordinates f(World world, int x, int y, int z, int range) {
        return new ChunkCoordinates(x, y, z);
    }
}
