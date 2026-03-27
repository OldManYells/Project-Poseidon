package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for resolving NMS world handles from Bukkit world views.
 */
public final class WorldHandleBridgeBehaviour {
    private static final WorldHandleBridgeBehaviour INSTANCE = new WorldHandleBridgeBehaviour();
    private static final WorldWrapperProjectionBridgeBehaviour WORLD_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR =
            WorldWrapperProjectionBridgeBehaviour.getInstance();

    private WorldHandleBridgeBehaviour() {
    }

    public static WorldHandleBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public <T> T resolveWorldServerHandle(Object world) {
        Object craftWorld = WORLD_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftWorld(BridgeReflection.cast(world));
        return BridgeReflection.cast(BridgeReflection.invoke(craftWorld, "getHandle"));
    }

    public <T> T resolveCraftWorld(Object world) {
        return BridgeReflection.cast(WORLD_WRAPPER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftWorld(BridgeReflection.cast(world)));
    }

    public byte resolveDimension(Object world) {
        Object worldHandle = resolveWorldServerHandle(world);
        return ((Number) BridgeReflection.getField(worldHandle, "dimension")).byteValue();
    }
}
