package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld spawn-location overload forwarding.
 */
public final class CraftWorldSpawnLocationBridgeBehaviour {
    private static final CraftWorldSpawnLocationBridgeBehaviour INSTANCE =
            new CraftWorldSpawnLocationBridgeBehaviour();

    private CraftWorldSpawnLocationBridgeBehaviour() {
    }

    public static CraftWorldSpawnLocationBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean setSpawnLocation(CraftWorld craftWorld, int x, int y, int z) {
        return craftWorld.setSpawnLocation(x, y, z, 0f, 0f);
    }
}
