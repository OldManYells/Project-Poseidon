package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftEventFactory spawn-area build permission checks.
 */
public final class SpawnBuildPermissionBehaviour {
    private static final SpawnBuildPermissionBehaviour INSTANCE = new SpawnBuildPermissionBehaviour();

    private SpawnBuildPermissionBehaviour() {
    }

    public static SpawnBuildPermissionBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canBuild(CraftWorld world, Player player, int x, int z) {
        WorldServer worldServer = world.getHandle().getHandle();
        int spawnSize = Bukkit.getServer().getSpawnRadius();

        if (spawnSize <= 0) {
            return true;
        }
        if (Boolean.TRUE.equals(BridgeReflection.invoke(player, "isOp"))) {
            return true;
        }

        ChunkCoordinates spawn = worldServer.getSpawn();
        int distanceFromSpawn = (int) Math.max(Math.abs(x - spawn.x), Math.abs(z - spawn.z));
        return distanceFromSpawn > spawnSize;
    }
}
