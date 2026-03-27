package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block-state coordinate projection.
 */
public final class BlockStateCoordinateProjectionBehaviour {
    private static final BlockStateCoordinateProjectionBehaviour INSTANCE = new BlockStateCoordinateProjectionBehaviour();

    private BlockStateCoordinateProjectionBehaviour() {
    }

    public static BlockStateCoordinateProjectionBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T getLocation(Object world, int x, int y, int z) {
        Object block = BridgeReflection.invoke(world, "getBlockAt", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
        return BridgeReflection.cast(BridgeReflection.invoke(block, "getLocation"));
    }
}
