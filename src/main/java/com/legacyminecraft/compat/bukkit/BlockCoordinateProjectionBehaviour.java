package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block coordinate projection helpers.
 */
public final class BlockCoordinateProjectionBehaviour {
    private static final BlockCoordinateProjectionBehaviour INSTANCE = new BlockCoordinateProjectionBehaviour();

    private BlockCoordinateProjectionBehaviour() {
    }

    public static BlockCoordinateProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    public Location createLocation(World world, int x, int y, int z) {
        return new Location(world, x, y, z);
    }

    public BlockVector createVector(int x, int y, int z) {
        return new BlockVector(x, y, z);
    }
}
