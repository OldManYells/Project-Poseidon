package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld spawn-location update and event dispatch flow.
 */
public final class CraftWorldSpawnUpdateBehaviour {
    private static final CraftWorldSpawnUpdateBehaviour INSTANCE = new CraftWorldSpawnUpdateBehaviour();

    private CraftWorldSpawnUpdateBehaviour() {
    }

    public static CraftWorldSpawnUpdateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean setSpawnLocation(World world, Server server, WorldServer worldServer, int x, int y, int z, float yaw, float pitch) {
        try {
            Location previousLocation = world.getSpawnLocation();
            worldServer.worldData.setSpawn(x, y, z, yaw, pitch);
            SpawnChangeEvent event = new SpawnChangeEvent(world, previousLocation);
            server.getPluginManager().callEvent(event);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }
}
