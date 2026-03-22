package net.minecraft.server;

import com.legacyminecraft.poseidon.world.chunk.ChunkProviderCacheBehaviour;
import com.legacyminecraft.poseidon.world.chunk.ChunkPopulationTriggerBehaviour;
import com.legacyminecraft.poseidon.world.chunk.ChunkProviderPersistencePolicyBehaviour;

import java.util.*;

public class ChunkProviderLoadOrGenerate implements IChunkProvider {
    private static final ChunkProviderCacheBehaviour CHUNK_PROVIDER_CACHE_BEHAVIOUR = ChunkProviderCacheBehaviour.getInstance();
    private static final ChunkPopulationTriggerBehaviour CHUNK_POPULATION_TRIGGER_BEHAVIOUR = ChunkPopulationTriggerBehaviour.getInstance();
    private static final ChunkProviderPersistencePolicyBehaviour CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR = ChunkProviderPersistencePolicyBehaviour.getInstance();

    private Set a = new HashSet();
    private Chunk b;
    private IChunkProvider c;
    private IChunkLoader d;
    private Map e = new HashMap();
    private List f = new ArrayList();
    private World g;

    public ChunkProviderLoadOrGenerate(World world, IChunkLoader ichunkloader, IChunkProvider ichunkprovider) {
        this.b = new EmptyChunk(world, new byte['\u8000'], 0, 0);
        this.g = world;
        this.d = ichunkloader;
        this.c = ichunkprovider;
    }

    public boolean isChunkLoaded(int i, int j) {
        return CHUNK_PROVIDER_CACHE_BEHAVIOUR.isLoaded(this.e, i, j);
    }

    public Chunk getChunkAt(int i, int j) {
        CHUNK_PROVIDER_CACHE_BEHAVIOUR.removeUnloadRequest(this.a, i, j);
        Chunk chunk = CHUNK_PROVIDER_CACHE_BEHAVIOUR.getLoadedChunk(this.e, i, j);

        if (chunk == null) {
            chunk = this.d(i, j);
            if (chunk == null) {
                if (this.c == null) {
                    chunk = this.b;
                } else {
                    chunk = this.c.getOrCreateChunk(i, j);
                }
            }

            CHUNK_PROVIDER_CACHE_BEHAVIOUR.cacheChunk(this.e, this.f, i, j, chunk);
            if (chunk != null) {
                chunk.loadNOP();
                chunk.addEntities();
            }

            boolean hasSouthEast = this.isChunkLoaded(i + 1, j + 1);
            boolean hasSouth = this.isChunkLoaded(i, j + 1);
            boolean hasEast = this.isChunkLoaded(i + 1, j);
            if (CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateCurrentChunk(chunk, hasSouthEast && hasSouth && hasEast)) {
                this.getChunkAt(this, i, j);
            }

            boolean hasWest = this.isChunkLoaded(i - 1, j);
            boolean westDone = hasWest && this.getOrCreateChunk(i - 1, j).done;
            boolean hasSouthWest = this.isChunkLoaded(i - 1, j + 1);
            if (CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateWestNeighbor(hasWest, westDone, hasSouthWest, hasSouth)) {
                this.getChunkAt(this, i - 1, j);
            }

            boolean hasNorth = this.isChunkLoaded(i, j - 1);
            boolean northDone = hasNorth && this.getOrCreateChunk(i, j - 1).done;
            boolean hasNorthEast = this.isChunkLoaded(i + 1, j - 1);
            if (CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateNorthNeighbor(hasNorth, northDone, hasNorthEast, hasEast)) {
                this.getChunkAt(this, i, j - 1);
            }

            boolean hasNorthWest = this.isChunkLoaded(i - 1, j - 1);
            boolean northWestDone = hasNorthWest && this.getOrCreateChunk(i - 1, j - 1).done;
            if (CHUNK_POPULATION_TRIGGER_BEHAVIOUR.shouldPopulateNorthWestNeighbor(hasNorthWest, northWestDone, hasNorth, hasWest)) {
                this.getChunkAt(this, i - 1, j - 1);
            }
        }

        return chunk;
    }

    public Chunk getOrCreateChunk(int i, int j) {
        Chunk chunk = CHUNK_PROVIDER_CACHE_BEHAVIOUR.getLoadedChunk(this.e, i, j);

        return chunk == null ? this.getChunkAt(i, j) : chunk;
    }

    private Chunk d(int i, int j) {
        if (this.d == null) {
            return null;
        } else {
            try {
                Chunk chunk = this.d.a(this.g, i, j);

                if (chunk != null) {
                    chunk.r = this.g.getTime();
                }

                return chunk;
            } catch (Exception exception) {
                exception.printStackTrace();
                return null;
            }
        }
    }

    private void a(Chunk chunk) {
        if (this.d != null) {
            try {
                this.d.b(this.g, chunk);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private void b(Chunk chunk) {
        if (this.d != null) {
            try {
                chunk.r = this.g.getTime();
                this.d.a(this.g, chunk);
            } catch (Exception ioexception) {
                ioexception.printStackTrace();
            }
        }
    }

    public void getChunkAt(IChunkProvider ichunkprovider, int i, int j) {
        Chunk chunk = this.getOrCreateChunk(i, j);

        if (!chunk.done) {
            chunk.done = true;
            if (this.c != null) {
                this.c.getChunkAt(ichunkprovider, i, j);
                chunk.f();
            }
        }
    }

    public boolean saveChunks(boolean flag, IProgressUpdate iprogressupdate) {
        int i = 0;

        for (int j = 0; j < this.f.size(); ++j) {
            Chunk chunk = (Chunk) this.f.get(j);

            if (CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.shouldWriteChunkMetadata(flag, chunk.p)) {
                this.a(chunk);
            }

            if (chunk.a(flag)) {
                this.b(chunk);
                chunk.o = false;
                ++i;
                if (CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.shouldStopIncrementalSave(i, flag)) {
                    return false;
                }
            }
        }

        if (CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.shouldFlushChunkLoader(flag, this.d)) {
            this.d.b();
        }

        return true;
    }

    public boolean unloadChunks() {
        for (int i = 0; i < CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.maxChunksPerUnloadPass(); ++i) {
            if (CHUNK_PROVIDER_PERSISTENCE_POLICY_BEHAVIOUR.hasQueuedUnloads(this.a.isEmpty())) {
                Integer integer = (Integer) this.a.iterator().next();
                Chunk chunk = (Chunk) this.e.get(integer);

                chunk.removeEntities();
                this.b(chunk);
                this.a(chunk);
                this.a.remove(integer);
                this.e.remove(integer);
                this.f.remove(chunk);
            }
        }

        if (this.d != null) {
            this.d.a();
        }

        return this.c.unloadChunks();
    }

    public boolean canSave() {
        return true;
    }
}
