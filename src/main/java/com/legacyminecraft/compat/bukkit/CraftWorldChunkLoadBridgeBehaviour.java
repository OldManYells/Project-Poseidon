package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld chunk-load overload forwarding.
 */
public final class CraftWorldChunkLoadBridgeBehaviour {
    private static final CraftWorldChunkLoadBridgeBehaviour INSTANCE =
            new CraftWorldChunkLoadBridgeBehaviour();

    private CraftWorldChunkLoadBridgeBehaviour() {
    }

    public static CraftWorldChunkLoadBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public void loadChunk(CraftWorld craftWorld, int x, int z) {
        craftWorld.loadChunk(x, z, true);
    }
}
