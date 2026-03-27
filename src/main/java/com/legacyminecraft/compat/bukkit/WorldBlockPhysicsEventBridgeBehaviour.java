package com.legacyminecraft.compat.bukkit;


/**
 * Canonical Bukkit bridge behaviour for block-physics events raised by world updates.
 */
public final class WorldBlockPhysicsEventBridgeBehaviour {
    private static final WorldBlockPhysicsEventBridgeBehaviour INSTANCE = new WorldBlockPhysicsEventBridgeBehaviour();
    private static final WorldServerProjectionBridgeBehaviour WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR =
            WorldServerProjectionBridgeBehaviour.getInstance();

    private WorldBlockPhysicsEventBridgeBehaviour() {
    }

    public static WorldBlockPhysicsEventBridgeBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldCancelPhysics(Object world, int x, int y, int z, int sourceTypeId) {
        Object craftWorld = WORLD_SERVER_PROJECTION_BRIDGE_BEHAVIOUR.resolveCraftWorld(world);
        if (craftWorld == null) {
            return false;
        }

        try {
            Object block = craftWorld.getClass().getMethod("getBlockAt", Integer.TYPE, Integer.TYPE, Integer.TYPE)
                    .invoke(craftWorld, Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z));
            Class<?> eventClass = Class.forName("org.bukkit.event.block.BlockPhysicsEvent");
            Object event = eventClass
                    .getConstructor(Class.forName("org.bukkit.block.Block"), Integer.TYPE)
                    .newInstance(block, Integer.valueOf(sourceTypeId));
            Object server = world.getClass().getMethod("getServer").invoke(world);
            Object pluginManager = server.getClass().getMethod("getPluginManager").invoke(server);
            pluginManager.getClass().getMethod("callEvent", Class.forName("org.bukkit.event.Event")).invoke(pluginManager, event);
            return ((Boolean) eventClass.getMethod("isCancelled").invoke(event)).booleanValue();
        } catch (ReflectiveOperationException ignored) {
            return false;
        }
    }
}
