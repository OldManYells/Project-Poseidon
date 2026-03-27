package com.legacyminecraft.compat.bukkit;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Compat chunk provider scaffold used by CraftWorld behaviours.
 */
public class ChunkProviderServer implements IChunkProvider {
    public final ChunkKeySet unloadQueue = new ChunkKeySet();
    public final ChunkKeyMap chunks = new ChunkKeyMap();
    public final List<Chunk> chunkList = new ArrayList<Chunk>();

    public IChunkProvider chunkProvider;
    public final Chunk emptyChunk = new Chunk();

    public final WorldServer world;
    private final IChunkLoader chunkLoader;

    public ChunkProviderServer(WorldServer world, IChunkLoader chunkLoader, IChunkProvider chunkProvider) {
        this.world = world;
        this.chunkLoader = chunkLoader;
        this.chunkProvider = chunkProvider;
    }

    @Override
    public boolean isChunkLoaded(int chunkX, int chunkZ) {
        return chunks.get(chunkX, chunkZ) != null;
    }

    @Override
    public Chunk getOrCreateChunk(int chunkX, int chunkZ) {
        Chunk chunk = chunks.get(chunkX, chunkZ);
        if (chunk != null) {
            return chunk;
        }
        if (chunkProvider != null) {
            chunk = chunkProvider.getOrCreateChunk(chunkX, chunkZ);
            if (chunk != null) {
                chunks.put(chunkX, chunkZ, chunk);
                return chunk;
            }
        }
        chunk = new Chunk(world, new byte[0], chunkX, chunkZ);
        chunks.put(chunkX, chunkZ, chunk);
        return chunk;
    }

    @Override
    public Chunk getChunkAt(int chunkX, int chunkZ) {
        Chunk chunk = chunks.get(chunkX, chunkZ);
        if (chunk != null) {
            return chunk;
        }
        return getOrCreateChunk(chunkX, chunkZ);
    }

    @Override
    public void getChunkAt(IChunkProvider requester, int chunkX, int chunkZ) {
        getChunkAt(chunkX, chunkZ);
    }

    public Chunk loadChunk(int chunkX, int chunkZ) {
        if (chunkLoader == null) {
            return null;
        }
        return chunkLoader.loadChunk(world, chunkX, chunkZ);
    }

    public void queueUnload(int chunkX, int chunkZ) {
        unloadQueue.add(chunkX, chunkZ);
    }

    public void saveChunk(Chunk chunk) {
        if (chunkLoader != null) {
            chunkLoader.saveChunk(world, chunk);
        }
    }

    public void saveChunkNOP(Chunk chunk) {
        if (chunkLoader != null) {
            chunkLoader.saveChunkNOP(world, chunk);
        }
    }

    @Override
    public boolean saveChunks(boolean save, IProgressUpdate progressUpdate) {
        return true;
    }

    @Override
    public boolean unloadChunks() {
        return true;
    }

    @Override
    public boolean canSave() {
        return true;
    }

    public static final class ChunkKeySet {
        private final java.util.Set<Long> keys = new java.util.HashSet<Long>();

        public void add(int chunkX, int chunkZ) {
            keys.add(pack(chunkX, chunkZ));
        }

        public void remove(int chunkX, int chunkZ) {
            keys.remove(pack(chunkX, chunkZ));
        }

        private long pack(int chunkX, int chunkZ) {
            return (((long) chunkX) << 32) ^ (chunkZ & 0xFFFFFFFFL);
        }
    }

    public static final class ChunkKeyMap {
        private final Map<Long, Chunk> map = new HashMap<Long, Chunk>();

        public Chunk get(int chunkX, int chunkZ) {
            return map.get(pack(chunkX, chunkZ));
        }

        public void put(int chunkX, int chunkZ, Chunk chunk) {
            map.put(pack(chunkX, chunkZ), chunk);
        }

        public void remove(int chunkX, int chunkZ) {
            map.remove(pack(chunkX, chunkZ));
        }

        public Collection<Chunk> values() {
            return map.values();
        }

        private long pack(int chunkX, int chunkZ) {
            return (((long) chunkX) << 32) ^ (chunkZ & 0xFFFFFFFFL);
        }
    }
}
