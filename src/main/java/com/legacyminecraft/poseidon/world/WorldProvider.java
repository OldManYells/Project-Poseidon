package com.legacyminecraft.poseidon.world;

/**
 * Minimal world-provider scaffold used by migrated world/map logic.
 */
public class WorldProvider {
    public int dimension;
    public boolean e;
    private int spawnX;
    private int spawnZ;

    public int c() {
        return spawnX;
    }

    public int e() {
        return spawnZ;
    }

    public void setSpawn(int x, int z) {
        this.spawnX = x;
        this.spawnZ = z;
    }

    public boolean canSpawn(int x, int z) {
        return true;
    }
}
