package com.legacyminecraft.compat.bukkit;


import net.minecraft.server.ChunkCoordinates;
import net.minecraft.server.WorldServer;

/**
 * Canonical behaviour for CraftServer spawn-preparation wrapper glue.
 */
public final class CraftServerWorldSpawnPreparationBehaviour {
    private static final CraftServerWorldSpawnPreparationBehaviour INSTANCE =
            new CraftServerWorldSpawnPreparationBehaviour();

    private CraftServerWorldSpawnPreparationBehaviour() {
    }

    public static CraftServerWorldSpawnPreparationBehaviour getInstance() {
        return INSTANCE;
    }

    public void prepareSpawn(WorldServer worldServer, String name) {
        if (!worldServer.getWorld().getKeepSpawnInMemory()) {
            return;
        }

        short radius = 196;
        long intervalBase = System.currentTimeMillis();

        for (int xOffset = -radius; xOffset <= radius; xOffset += 16) {
            for (int zOffset = -radius; zOffset <= radius; zOffset += 16) {
                long now = System.currentTimeMillis();

                if (now < intervalBase) {
                    intervalBase = now;
                }

                if (now > intervalBase + 1000L) {
                    int totalSteps = (radius * 2 + 1) * (radius * 2 + 1);
                    int step = (xOffset + radius) * (radius * 2 + 1) + zOffset + 1;

                    System.out.println("Preparing spawn area for " + name + ", " + (step * 100 / totalSteps) + "%");
                    intervalBase = now;
                }

                ChunkCoordinates spawn = worldServer.getSpawn();
                worldServer.chunkProviderServer.getChunkAt(spawn.x + xOffset >> 4, spawn.z + zOffset >> 4);

                while (worldServer.doLighting()) {
                    // Keep tick-lighting until no pending light updates remain.
                }
            }
        }
    }
}
