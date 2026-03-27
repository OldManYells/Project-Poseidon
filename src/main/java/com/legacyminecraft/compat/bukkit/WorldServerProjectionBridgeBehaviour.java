package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for projecting NMS world handles to WorldServer-backed views.
 */
public final class WorldServerProjectionBridgeBehaviour {
    private static final WorldServerProjectionBridgeBehaviour INSTANCE = new WorldServerProjectionBridgeBehaviour();

    private WorldServerProjectionBridgeBehaviour() {
    }

    public static WorldServerProjectionBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public net.minecraft.server.WorldServer resolveWorldServer(Object world) {
        if (world == null) {
            return null;
        }
        String name = world.getClass().getName();
        if ("net.minecraft.server.WorldServer".equals(name) || name.endsWith(".WorldServer")) {
            return (net.minecraft.server.WorldServer) world;
        }
        return (net.minecraft.server.WorldServer) BridgeReflection.invoke(world, "getHandle");
    }

    public Object resolveCraftWorld(Object world) {
        Object worldServer = resolveWorldServer(world);
        return worldServer == null ? null : BridgeReflection.invoke(worldServer, "getWorld");
    }

    public Object resolveCraftServer(Object world) {
        Object worldServer = resolveWorldServer(world);
        return worldServer == null ? null : BridgeReflection.invoke(worldServer, "getServer");
    }

    public Object resolveEntityTracker(Object world) {
        Object worldServer = resolveWorldServer(world);
        return worldServer == null ? null : BridgeReflection.getField(worldServer, "tracker");
    }
}
