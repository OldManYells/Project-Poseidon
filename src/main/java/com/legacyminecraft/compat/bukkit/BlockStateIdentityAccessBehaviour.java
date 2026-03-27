package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for block-state identity and coordinate access.
 */
public final class BlockStateIdentityAccessBehaviour {
    private static final BlockStateIdentityAccessBehaviour INSTANCE = new BlockStateIdentityAccessBehaviour();

    private BlockStateIdentityAccessBehaviour() {
    }

    public static BlockStateIdentityAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T getWorld(Object world) {
        return BridgeReflection.cast(world);
    }

    public int getX(int x) {
        return x;
    }

    public int getY(int y) {
        return y;
    }

    public int getZ(int z) {
        return z;
    }

    public <T> T getChunk(Object chunk) {
        return BridgeReflection.cast(chunk);
    }
}
