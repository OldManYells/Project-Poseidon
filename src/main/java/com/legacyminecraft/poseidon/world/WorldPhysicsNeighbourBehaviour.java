package com.legacyminecraft.poseidon.world;

/**
 * Canonical behaviour for world physics neighbor-coordinate policy.
 */
public final class WorldPhysicsNeighbourBehaviour {
    private static final WorldPhysicsNeighbourBehaviour INSTANCE = new WorldPhysicsNeighbourBehaviour();

    private WorldPhysicsNeighbourBehaviour() {
    }

    public static WorldPhysicsNeighbourBehaviour getInstance() {
        return INSTANCE;
    }

    public int[][] cardinalNeighbors(int x, int y, int z) {
        return new int[][]{
                { x - 1, y, z },
                { x + 1, y, z },
                { x, y - 1, z },
                { x, y + 1, z },
                { x, y, z - 1 },
                { x, y, z + 1 }
        };
    }
}
