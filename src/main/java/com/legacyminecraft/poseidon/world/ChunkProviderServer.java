package com.legacyminecraft.poseidon.world;

/**
 * World-local chunk provider alias.
 */
public class ChunkProviderServer extends com.legacyminecraft.compat.bukkit.ChunkProviderServer {
    public ChunkProviderServer(WorldServer world, IChunkLoader chunkLoader, IChunkProvider chunkProvider) {
        super(
                new com.legacyminecraft.compat.bukkit.WorldServer(),
                chunkLoader,
                adapt(chunkProvider)
        );
    }

    private static com.legacyminecraft.compat.bukkit.IChunkProvider adapt(final IChunkProvider provider) {
        if (provider == null) {
            return null;
        }

        return new com.legacyminecraft.compat.bukkit.IChunkProvider() {
            @Override
            public boolean isChunkLoaded(int chunkX, int chunkZ) {
                return provider.isChunkLoaded(chunkX, chunkZ);
            }

            @Override
            public com.legacyminecraft.compat.bukkit.Chunk getOrCreateChunk(int chunkX, int chunkZ) {
                return toCompatChunk(provider.getOrCreateChunk(chunkX, chunkZ));
            }

            @Override
            public com.legacyminecraft.compat.bukkit.Chunk getChunkAt(int chunkX, int chunkZ) {
                return toCompatChunk(provider.getChunkAt(chunkX, chunkZ));
            }

            @Override
            public void getChunkAt(com.legacyminecraft.compat.bukkit.IChunkProvider requester, int chunkX, int chunkZ) {
                provider.getChunkAt(adapt(requester), chunkX, chunkZ);
            }

            @Override
            public boolean saveChunks(boolean save, com.legacyminecraft.compat.bukkit.IProgressUpdate progressUpdate) {
                return provider.saveChunks(save, progressUpdate instanceof IProgressUpdate ? (IProgressUpdate) progressUpdate : null);
            }

            @Override
            public boolean unloadChunks() {
                return provider.unloadChunks();
            }

            @Override
            public boolean canSave() {
                return provider.canSave();
            }
        };
    }

    private static IChunkProvider adapt(final com.legacyminecraft.compat.bukkit.IChunkProvider provider) {
        if (provider == null) {
            return null;
        }

        return new IChunkProvider() {
            @Override
            public boolean isChunkLoaded(int chunkX, int chunkZ) {
                return provider.isChunkLoaded(chunkX, chunkZ);
            }

            @Override
            public Chunk getOrCreateChunk(int chunkX, int chunkZ) {
                return toPoseidonChunk(provider.getOrCreateChunk(chunkX, chunkZ));
            }

            @Override
            public Chunk getChunkAt(int chunkX, int chunkZ) {
                return toPoseidonChunk(provider.getChunkAt(chunkX, chunkZ));
            }

            @Override
            public void getChunkAt(IChunkProvider requester, int chunkX, int chunkZ) {
                // Compat providers do not expose recursive generation hooks.
            }

            @Override
            public boolean saveChunks(boolean save, IProgressUpdate progressUpdate) {
                return provider.saveChunks(save, progressUpdate);
            }

            @Override
            public boolean unloadChunks() {
                return provider.unloadChunks();
            }

            @Override
            public boolean canSave() {
                return provider.canSave();
            }

            @Override
            public void populate(IChunkProvider provider, int chunkX, int chunkZ) {
            }
        };
    }

    private static com.legacyminecraft.compat.bukkit.Chunk toCompatChunk(Chunk chunk) {
        if (chunk == null) {
            return null;
        }

        com.legacyminecraft.compat.bukkit.Chunk compatChunk = new com.legacyminecraft.compat.bukkit.Chunk();
        compatChunk.x = chunk.x;
        compatChunk.z = chunk.z;
        compatChunk.worldServer = new com.legacyminecraft.compat.bukkit.WorldServer();
        return compatChunk;
    }

    private static Chunk toPoseidonChunk(com.legacyminecraft.compat.bukkit.Chunk chunk) {
        if (chunk == null) {
            return null;
        }

        return new Chunk(new World(), chunk.getX(), chunk.getZ());
    }
}
