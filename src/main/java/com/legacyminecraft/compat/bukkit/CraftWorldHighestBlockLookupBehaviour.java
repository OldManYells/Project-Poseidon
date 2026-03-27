package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld highest-block lookup wrapper glue.
 */
public final class CraftWorldHighestBlockLookupBehaviour {
    private static final CraftWorldHighestBlockLookupBehaviour INSTANCE =
            new CraftWorldHighestBlockLookupBehaviour();

    private CraftWorldHighestBlockLookupBehaviour() {
    }

    public static CraftWorldHighestBlockLookupBehaviour getInstance() {
        return INSTANCE;
    }

    public Block getHighestBlockAt(CraftWorld craftWorld, int x, int z) {
        return craftWorld.getBlockAt(x, craftWorld.getHighestBlockYAt(x, z), z);
    }
}
