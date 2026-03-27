package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block relative lookup helpers.
 */
public final class BlockRelativeLookupBehaviour {
    private static final BlockRelativeLookupBehaviour INSTANCE = new BlockRelativeLookupBehaviour();

    private BlockRelativeLookupBehaviour() {
    }

    public static BlockRelativeLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public Block getRelative(World world, int x, int y, int z, int modX, int modY, int modZ) {
        return world.getBlockAt(x + modX, y + modY, z + modZ);
    }

    public Block getFace(World world, int x, int y, int z, BlockFace face) {
        return getRelative(world, x, y, z, face, 1);
    }

    public Block getFace(World world, int x, int y, int z, BlockFace face, int distance) {
        return getRelative(world, x, y, z, face, distance);
    }

    public Block getRelative(World world, int x, int y, int z, BlockFace face, int distance) {
        return getRelative(
                world,
                x,
                y,
                z,
                face.getModX() * distance,
                face.getModY() * distance,
                face.getModZ() * distance
        );
    }
}
