package com.legacyminecraft.poseidon.world;


/**
 * Canonical behaviour for world chunk-window loaded checks.
 */
public final class WorldChunkLoadWindowBehaviour {
    private static final WorldChunkLoadWindowBehaviour INSTANCE = new WorldChunkLoadWindowBehaviour();

    private WorldChunkLoadWindowBehaviour() {
    }

    public static WorldChunkLoadWindowBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean isBlockYInLoadedRange(int y) {
        return y >= 0 && y < 128;
    }

    public boolean isChunkLoaded(IChunkProvider chunkProvider, int chunkX, int chunkZ) {
        return chunkProvider.isChunkLoaded(chunkX, chunkZ);
    }

    public boolean isBlockLoaded(IChunkProvider chunkProvider, int x, int y, int z) {
        return this.isBlockYInLoadedRange(y) && this.isChunkLoaded(chunkProvider, x >> 4, z >> 4);
    }

    public boolean areChunksLoaded(
            IChunkProvider chunkProvider,
            int minX,
            int minY,
            int minZ,
            int maxX,
            int maxY,
            int maxZ
    ) {
        if (maxY < 0 || minY >= 128) {
            return false;
        }

        int minChunkX = minX >> 4;
        int maxChunkX = maxX >> 4;
        int minChunkZ = minZ >> 4;
        int maxChunkZ = maxZ >> 4;

        for (int chunkX = minChunkX; chunkX <= maxChunkX; ++chunkX) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; ++chunkZ) {
                if (!this.isChunkLoaded(chunkProvider, chunkX, chunkZ)) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean areChunksLoadedAround(IChunkProvider chunkProvider, int x, int y, int z, int radius) {
        return this.areChunksLoaded(
                chunkProvider,
                x - radius,
                y - radius,
                z - radius,
                x + radius,
                y + radius,
                z + radius
        );
    }
}
