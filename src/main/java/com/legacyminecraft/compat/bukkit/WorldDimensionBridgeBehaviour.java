package com.legacyminecraft.compat.bukkit;


import java.lang.reflect.Method;

/**
 * Canonical compat bridge for resolving legacy world dimension ids.
 */
public final class WorldDimensionBridgeBehaviour {
    private static final WorldDimensionBridgeBehaviour INSTANCE = new WorldDimensionBridgeBehaviour();

    private WorldDimensionBridgeBehaviour() {
    }

    public static WorldDimensionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveDimension(Object world, int fallback) {
        Object worldHandle = resolveWorldHandle(world, null);
        if (worldHandle == null) {
            return fallback;
        }
        return ((Number) BridgeReflection.getField(worldHandle, "dimension")).intValue();
    }

    public Object resolveWorldHandle(Object world, Object fallback) {
        if (world == null) {
            return fallback;
        }
        try {
            Method getHandleMethod = world.getClass().getMethod("getHandle");
            Object worldHandle = getHandleMethod.invoke(world);
            return worldHandle == null ? fallback : worldHandle;
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
