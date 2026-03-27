package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.World;

/**
 * Canonical behaviour for minecart track metadata composition/writes.
 */
public final class MinecartTrackDataWriteBehaviour {
    private static final MinecartTrackDataWriteBehaviour INSTANCE = new MinecartTrackDataWriteBehaviour();

    private MinecartTrackDataWriteBehaviour() {
    }

    public static MinecartTrackDataWriteBehaviour getInstance() {
        return INSTANCE;
    }

    public int composeStoredData(World world, int x, int y, int z, boolean poweredRail, byte shape) {
        if (poweredRail) {
            return world.getData(x, y, z) & 8 | shape;
        }

        return shape;
    }

    public boolean shouldWriteData(boolean forceWrite, World world, int x, int y, int z, int composedData) {
        return forceWrite || world.getData(x, y, z) != composedData;
    }

    public void writeData(World world, int x, int y, int z, int composedData) {
        world.setData(x, y, z, composedData);
    }
}
