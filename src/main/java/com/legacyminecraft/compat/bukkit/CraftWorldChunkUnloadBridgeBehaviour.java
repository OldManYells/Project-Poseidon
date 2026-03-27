package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld chunk-unload overload forwarding.
 */
public final class CraftWorldChunkUnloadBridgeBehaviour {
    private static final CraftWorldChunkUnloadBridgeBehaviour INSTANCE =
            new CraftWorldChunkUnloadBridgeBehaviour();

    private CraftWorldChunkUnloadBridgeBehaviour() {
    }

    public static CraftWorldChunkUnloadBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean unloadChunk(CraftWorld craftWorld, int x, int z) {
        return craftWorld.unloadChunk(x, z, true);
    }

    public boolean unloadChunk(CraftWorld craftWorld, int x, int z, boolean save) {
        return craftWorld.unloadChunk(x, z, save, false);
    }

    public boolean unloadChunkRequest(CraftWorld craftWorld, int x, int z) {
        return craftWorld.unloadChunkRequest(x, z, true);
    }
}
