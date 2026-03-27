package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behavior for CraftWorld spawn-chunk keep-loaded orchestration.
 */
public final class CraftWorldSpawnChunkRetentionBehaviour {
    private static final CraftWorldSpawnChunkRetentionBehaviour INSTANCE = new CraftWorldSpawnChunkRetentionBehaviour();

    private CraftWorldSpawnChunkRetentionBehaviour() {
    }

    public static CraftWorldSpawnChunkRetentionBehaviour getInstance() {
        return INSTANCE;
    }

    public void setKeepSpawnInMemory(CraftWorld craftWorld, WorldServer worldServer, boolean keepLoaded) {
        worldServer.keepSpawnInMemory = keepLoaded;

        ChunkCoordinates spawnCoordinates = worldServer.getSpawn();
        int spawnChunkX = spawnCoordinates.x >> 4;
        int spawnChunkZ = spawnCoordinates.z >> 4;

        for (int xOffset = -12; xOffset <= 12; xOffset++) {
            for (int zOffset = -12; zOffset <= 12; zOffset++) {
                int chunkX = spawnChunkX + xOffset;
                int chunkZ = spawnChunkZ + zOffset;
                if (keepLoaded) {
                    craftWorld.loadChunk(chunkX, chunkZ);
                    continue;
                }

                if (!craftWorld.isChunkLoaded(chunkX, chunkZ)) {
                    continue;
                }

                if (worldServer.getChunkAt(chunkX, chunkZ).isEmpty()) {
                    craftWorld.unloadChunk(chunkX, chunkZ, false);
                } else {
                    craftWorld.unloadChunk(chunkX, chunkZ);
                }
            }
        }
    }
}
