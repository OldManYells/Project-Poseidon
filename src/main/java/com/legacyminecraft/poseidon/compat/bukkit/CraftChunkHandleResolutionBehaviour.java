package com.legacyminecraft.poseidon.compat.bukkit;

import net.minecraft.server.WorldServer;

import java.lang.ref.WeakReference;

/**
 * Canonical behavior for CraftChunk weak-handle resolution and refresh.
 */
public final class CraftChunkHandleResolutionBehaviour {
    private static final CraftChunkHandleResolutionBehaviour INSTANCE = new CraftChunkHandleResolutionBehaviour();

    private CraftChunkHandleResolutionBehaviour() {
    }

    public static CraftChunkHandleResolutionBehaviour getInstance() {
        return INSTANCE;
    }

    public ResolutionResult resolveHandle(
            WeakReference<net.minecraft.server.Chunk> weakChunk,
            WorldServer worldServer,
            int chunkX,
            int chunkZ
    ) {
        net.minecraft.server.Chunk chunk = weakChunk.get();
        if (chunk == null) {
            chunk = worldServer.getChunkAt(chunkX, chunkZ);
            weakChunk = new WeakReference<net.minecraft.server.Chunk>(chunk);
        }
        return new ResolutionResult(chunk, weakChunk);
    }

    public static final class ResolutionResult {
        private final net.minecraft.server.Chunk chunk;
        private final WeakReference<net.minecraft.server.Chunk> refreshedWeakChunk;

        public ResolutionResult(
                net.minecraft.server.Chunk chunk,
                WeakReference<net.minecraft.server.Chunk> refreshedWeakChunk
        ) {
            this.chunk = chunk;
            this.refreshedWeakChunk = refreshedWeakChunk;
        }

        public net.minecraft.server.Chunk getChunk() {
            return chunk;
        }

        public WeakReference<net.minecraft.server.Chunk> getRefreshedWeakChunk() {
            return refreshedWeakChunk;
        }
    }
}
