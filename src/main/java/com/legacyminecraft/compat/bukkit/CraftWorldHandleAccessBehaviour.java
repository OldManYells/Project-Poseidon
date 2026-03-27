package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld legacy handle/tile-entity wrapper glue.
 */
public final class CraftWorldHandleAccessBehaviour {
    private static final CraftWorldHandleAccessBehaviour INSTANCE = new CraftWorldHandleAccessBehaviour();

    private CraftWorldHandleAccessBehaviour() {
    }

    public static CraftWorldHandleAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public WorldServer getHandle(WorldServer worldServer) {
        return worldServer;
    }

    public TileEntity getTileEntityAt(WorldServer worldServer, int x, int y, int z) {
        return worldServer.getTileEntity(x, y, z);
    }
}
