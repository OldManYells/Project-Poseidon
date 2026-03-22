package com.legacyminecraft.poseidon.world;

import net.minecraft.server.Chunk;
import net.minecraft.server.IChunkProvider;
import net.minecraft.server.World;

/**
 * Canonical behaviour for world chunk access cache hit/miss resolution.
 */
public final class WorldChunkAccessCacheBehaviour {
    private static final WorldChunkAccessCacheBehaviour INSTANCE = new WorldChunkAccessCacheBehaviour();

    private WorldChunkAccessCacheBehaviour() {
    }

    public static WorldChunkAccessCacheBehaviour getInstance() {
        return INSTANCE;
    }

    public ChunkAccessResult resolveCachedChunk(
            IChunkProvider chunkProvider,
            Chunk lastChunk,
            int lastChunkX,
            int lastChunkZ,
            int requestedChunkX,
            int requestedChunkZ
    ) {
        Chunk resolvedChunk = lastChunk;
        if (resolvedChunk == null || lastChunkX != requestedChunkX || lastChunkZ != requestedChunkZ) {
            resolvedChunk = chunkProvider.getOrCreateChunk(requestedChunkX, requestedChunkZ);
        }

        return new ChunkAccessResult(resolvedChunk, requestedChunkX, requestedChunkZ);
    }

    public Chunk getChunkAtWorldCoords(World world, int blockX, int blockZ) {
        return world.getChunkAt(blockX >> 4, blockZ >> 4);
    }

    public static final class ChunkAccessResult {
        public final Chunk chunk;
        public final int cachedChunkX;
        public final int cachedChunkZ;

        public ChunkAccessResult(Chunk chunk, int cachedChunkX, int cachedChunkZ) {
            this.chunk = chunk;
            this.cachedChunkX = cachedChunkX;
            this.cachedChunkZ = cachedChunkZ;
        }
    }
}
