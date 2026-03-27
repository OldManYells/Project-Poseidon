package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block-state live block lookup.
 */
public final class BlockStateBlockLookupBehaviour {
    private static final BlockStateBlockLookupBehaviour INSTANCE = new BlockStateBlockLookupBehaviour();

    private BlockStateBlockLookupBehaviour() {
    }

    public static BlockStateBlockLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T getBlock(Object world, int x, int y, int z) {
        return BridgeReflection.cast(BridgeReflection.invoke(world, "getBlockAt", Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z)));
    }
}
