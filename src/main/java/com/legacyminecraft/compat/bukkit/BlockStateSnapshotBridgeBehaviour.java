package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block-state snapshot bridge creation from world coordinates.
 */
public final class BlockStateSnapshotBridgeBehaviour {
    private static final BlockStateSnapshotBridgeBehaviour INSTANCE = new BlockStateSnapshotBridgeBehaviour();

    private BlockStateSnapshotBridgeBehaviour() {
    }

    public static BlockStateSnapshotBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T getBlockState(Object world, int x, int y, int z) {
        Object craftWorld = BridgeReflection.invoke(world, "getWorld");
        Object block = BridgeReflection.invoke(craftWorld, "getBlockAt", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
        return BridgeReflection.cast(BridgeReflection.invoke(block, "getState"));
    }
}
