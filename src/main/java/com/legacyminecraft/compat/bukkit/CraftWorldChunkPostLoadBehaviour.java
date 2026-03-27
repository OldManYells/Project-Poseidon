package com.legacyminecraft.compat.bukkit;


/**
 * Canonical behaviour for CraftWorld loaded-chunk post-processing and neighbor population triggers.
 */
public final class CraftWorldChunkPostLoadBehaviour {
    private static final CraftWorldChunkPostLoadBehaviour INSTANCE = new CraftWorldChunkPostLoadBehaviour();

    private CraftWorldChunkPostLoadBehaviour() {
    }

    public static CraftWorldChunkPostLoadBehaviour getInstance() {
        return INSTANCE;
    }

    public void postProcessLoadedChunk(ChunkProviderServer chunkProviderServer, Chunk loadedChunk, int chunkX, int chunkZ) {
        if (loadedChunk == null) {
            return;
        }

        chunkProviderServer.chunks.put(chunkX, chunkZ, loadedChunk);
        chunkProviderServer.chunkList.add(loadedChunk);

        loadedChunk.loadNOP();
        loadedChunk.addEntities();

        if (!loadedChunk.done
                && chunkProviderServer.isChunkLoaded(chunkX + 1, chunkZ + 1)
                && chunkProviderServer.isChunkLoaded(chunkX, chunkZ + 1)
                && chunkProviderServer.isChunkLoaded(chunkX + 1, chunkZ)) {
            chunkProviderServer.getChunkAt(chunkProviderServer, chunkX, chunkZ);
        }

        if (chunkProviderServer.isChunkLoaded(chunkX - 1, chunkZ)
                && !chunkProviderServer.getOrCreateChunk(chunkX - 1, chunkZ).done
                && chunkProviderServer.isChunkLoaded(chunkX - 1, chunkZ + 1)
                && chunkProviderServer.isChunkLoaded(chunkX, chunkZ + 1)
                && chunkProviderServer.isChunkLoaded(chunkX - 1, chunkZ)) {
            chunkProviderServer.getChunkAt(chunkProviderServer, chunkX - 1, chunkZ);
        }

        if (chunkProviderServer.isChunkLoaded(chunkX, chunkZ - 1)
                && !chunkProviderServer.getOrCreateChunk(chunkX, chunkZ - 1).done
                && chunkProviderServer.isChunkLoaded(chunkX + 1, chunkZ - 1)
                && chunkProviderServer.isChunkLoaded(chunkX, chunkZ - 1)
                && chunkProviderServer.isChunkLoaded(chunkX + 1, chunkZ)) {
            chunkProviderServer.getChunkAt(chunkProviderServer, chunkX, chunkZ - 1);
        }

        if (chunkProviderServer.isChunkLoaded(chunkX - 1, chunkZ - 1)
                && !chunkProviderServer.getOrCreateChunk(chunkX - 1, chunkZ - 1).done
                && chunkProviderServer.isChunkLoaded(chunkX - 1, chunkZ - 1)
                && chunkProviderServer.isChunkLoaded(chunkX, chunkZ - 1)
                && chunkProviderServer.isChunkLoaded(chunkX - 1, chunkZ)) {
            chunkProviderServer.getChunkAt(chunkProviderServer, chunkX - 1, chunkZ - 1);
        }
    }
}
