package com.legacyminecraft.poseidon.world.chunk;

import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkCoordIntPair;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Canonical behaviour for chunk provider keying and loaded-chunk cache bookkeeping.
 */
public final class ChunkProviderCacheBehaviour {
    private static final ChunkProviderCacheBehaviour INSTANCE = new ChunkProviderCacheBehaviour();

    private ChunkProviderCacheBehaviour() {
    }

    public static ChunkProviderCacheBehaviour getInstance() {
        return INSTANCE;
    }

    public int chunkKey(int chunkX, int chunkZ) {
        return ChunkCoordIntPair.a(chunkX, chunkZ);
    }

    public Integer boxedChunkKey(int chunkX, int chunkZ) {
        return Integer.valueOf(this.chunkKey(chunkX, chunkZ));
    }

    public boolean isLoaded(Map loadedChunks, int chunkX, int chunkZ) {
        return loadedChunks.containsKey(this.boxedChunkKey(chunkX, chunkZ));
    }

    public Chunk getLoadedChunk(Map loadedChunks, int chunkX, int chunkZ) {
        return (Chunk) loadedChunks.get(this.boxedChunkKey(chunkX, chunkZ));
    }

    public void removeUnloadRequest(Set queuedUnloads, int chunkX, int chunkZ) {
        queuedUnloads.remove(this.boxedChunkKey(chunkX, chunkZ));
    }

    public void cacheChunk(Map loadedChunks, List loadedChunkList, int chunkX, int chunkZ, Chunk chunk) {
        loadedChunks.put(this.boxedChunkKey(chunkX, chunkZ), chunk);
        loadedChunkList.add(chunk);
    }
}
