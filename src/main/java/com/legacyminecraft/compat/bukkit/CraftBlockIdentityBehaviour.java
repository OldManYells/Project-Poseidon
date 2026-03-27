package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftBlock identity and wrapper-string policy.
 */
public final class CraftBlockIdentityBehaviour {
    private static final CraftBlockIdentityBehaviour INSTANCE = new CraftBlockIdentityBehaviour();

    private CraftBlockIdentityBehaviour() {
    }

    public static CraftBlockIdentityBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isSameInstance(CraftBlock block, Object otherObject) {
        return block == otherObject;
    }

    public String toString(CraftChunk chunk, int x, int y, int z) {
        return "CraftBlock{" + "chunk=" + chunk + "x=" + x + "y=" + y + "z=" + z + '}';
    }
}
