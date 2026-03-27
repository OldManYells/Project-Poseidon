package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld spawn-location wrapper glue.
 */
public final class CraftWorldSpawnLocationAccessBehaviour {
    private static final CraftWorldSpawnLocationAccessBehaviour INSTANCE =
            new CraftWorldSpawnLocationAccessBehaviour();

    private CraftWorldSpawnLocationAccessBehaviour() {
    }

    public static CraftWorldSpawnLocationAccessBehaviour getInstance() {
        return INSTANCE;
    }

    public Location getSpawnLocation(World world, WorldServer worldServer) {
        ChunkCoordinates spawn = worldServer.getSpawn();
        float yaw = worldServer.worldData.getYaw();
        float pitch = worldServer.worldData.getPitch();
        return new Location(world, spawn.x, spawn.y, spawn.z, yaw, pitch);
    }
}
