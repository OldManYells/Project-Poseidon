package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.WorldServer;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.entity.Player;

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
        WorldServer worldServer = world.getHandle();
        int spawnSize = Bukkit.getServer().getSpawnRadius();

        if (spawnSize <= 0) {
            return true;
        }
        if (player.isOp()) {
            return true;
        }

        ChunkCoordinates spawn = worldServer.getSpawn();
        int distanceFromSpawn = (int) Math.max(Math.abs(x - spawn.x), Math.abs(z - spawn.z));
        return distanceFromSpawn > spawnSize;
    }
}
