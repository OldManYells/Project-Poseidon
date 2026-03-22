package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.WorldServer;
import org.bukkit.World;

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

    public int resolveDimension(World world, int fallback) {
        WorldServer worldHandle = resolveWorldHandle(world, null);
        if (worldHandle == null) {
            return fallback;
        }

        return worldHandle.dimension;
    }

    public WorldServer resolveWorldHandle(World world, WorldServer fallback) {
        if (world == null) {
            return fallback;
        }
        try {
            Method getHandleMethod = world.getClass().getMethod("getHandle");
            Object worldHandle = getHandleMethod.invoke(world);
            if (!(worldHandle instanceof WorldServer)) {
                return fallback;
            }

            return (WorldServer) worldHandle;
        } catch (Exception ignored) {
            return fallback;
        }
    }
}
