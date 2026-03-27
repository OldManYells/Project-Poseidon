package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting Bukkit world views to CraftWorld wrappers.
 */
public final class WorldWrapperProjectionBridgeBehaviour {
    private static final WorldWrapperProjectionBridgeBehaviour INSTANCE = new WorldWrapperProjectionBridgeBehaviour();

    private WorldWrapperProjectionBridgeBehaviour() {
    }

    public static WorldWrapperProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T resolveCraftWorld(Object world) {
        return BridgeReflection.cast(world);
    }
}
