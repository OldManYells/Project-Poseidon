package net.minecraft.server;

import com.legacyminecraft.poseidon.PoseidonConfig;
import com.legacyminecraft.compat.bukkit.ChunkEntitySliceCleanupBehaviour;
import com.legacyminecraft.poseidon.world.ChunkEntityLifecycleBehaviour;
import com.legacyminecraft.poseidon.world.ChunkEntityQueryBehaviour;
import com.legacyminecraft.poseidon.world.ChunkDataExtractionBehaviour;
import com.legacyminecraft.poseidon.world.ChunkSaveDecisionBehaviour;
import com.legacyminecraft.poseidon.world.ChunkSeededRandomBehaviour;
import com.legacyminecraft.poseidon.world.ChunkEntitySliceIndexBehaviour;
import com.legacyminecraft.poseidon.world.ChunkLightAccessBehaviour;
import com.legacyminecraft.poseidon.world.ChunkStateAccessBehaviour;
import com.legacyminecraft.poseidon.world.ChunkBlockMutationBehaviour;
import com.legacyminecraft.poseidon.world.ChunkSkyLightColumnUpdateBehaviour;
import com.legacyminecraft.poseidon.world.ChunkLightingInitializationBehaviour;
import com.legacyminecraft.poseidon.world.ChunkHeightColumnUpdateBehaviour;
import com.legacyminecraft.poseidon.world.ChunkTileEntityLifecycleBehaviour;
import com.legacyminecraft.poseidon.world.ChunkTileEntityLookupBehaviour;
import com.legacyminecraft.poseidon.world.WorldFeatureConfigPolicy;

import java.util.*;

public class Chunk extends com.legacyminecraft.poseidon.world.Chunk {
    private static final ChunkEntitySliceCleanupBehaviour CHUNK_ENTITY_SLICE_CLEANUP_BEHAVIOUR =
            ChunkEntitySliceCleanupBehaviour.getInstance();
    private static final ChunkEntityLifecycleBehaviour CHUNK_ENTITY_LIFECYCLE_BEHAVIOUR =
            ChunkEntityLifecycleBehaviour.getInstance();
    private static final ChunkTileEntityLifecycleBehaviour CHUNK_TILE_ENTITY_LIFECYCLE_BEHAVIOUR =
            ChunkTileEntityLifecycleBehaviour.getInstance();
    private static final ChunkTileEntityLookupBehaviour CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR =
            ChunkTileEntityLookupBehaviour.getInstance();
    private static final ChunkEntityQueryBehaviour CHUNK_ENTITY_QUERY_BEHAVIOUR =
            ChunkEntityQueryBehaviour.getInstance();
    private static final ChunkDataExtractionBehaviour CHUNK_DATA_EXTRACTION_BEHAVIOUR =
            ChunkDataExtractionBehaviour.getInstance();
    private static final ChunkEntitySliceIndexBehaviour CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR =
            ChunkEntitySliceIndexBehaviour.getInstance();
    private static final ChunkLightAccessBehaviour CHUNK_LIGHT_ACCESS_BEHAVIOUR =
            ChunkLightAccessBehaviour.getInstance();
    private static final ChunkStateAccessBehaviour CHUNK_STATE_ACCESS_BEHAVIOUR =
            ChunkStateAccessBehaviour.getInstance();
    private static final ChunkBlockMutationBehaviour CHUNK_BLOCK_MUTATION_BEHAVIOUR =
            ChunkBlockMutationBehaviour.getInstance();
    private static final ChunkSkyLightColumnUpdateBehaviour CHUNK_SKY_LIGHT_COLUMN_UPDATE_BEHAVIOUR =
            ChunkSkyLightColumnUpdateBehaviour.getInstance();
    private static final ChunkLightingInitializationBehaviour CHUNK_LIGHTING_INITIALIZATION_BEHAVIOUR =
            ChunkLightingInitializationBehaviour.getInstance();
    private static final ChunkHeightColumnUpdateBehaviour CHUNK_HEIGHT_COLUMN_UPDATE_BEHAVIOUR =
            ChunkHeightColumnUpdateBehaviour.getInstance();
    private static final ChunkSaveDecisionBehaviour CHUNK_SAVE_DECISION_BEHAVIOUR =
            ChunkSaveDecisionBehaviour.getInstance();
    private static final ChunkSeededRandomBehaviour CHUNK_SEEDED_RANDOM_BEHAVIOUR =
            ChunkSeededRandomBehaviour.getInstance();
    private static final WorldFeatureConfigPolicy WORLD_FEATURE_CONFIG_POLICY = WorldFeatureConfigPolicy.getInstance();

    public static boolean a;
    public byte[] b;
    public boolean c;
    public World world;
    public NibbleArray e;
    public NibbleArray f;
    public NibbleArray g;
    public byte[] heightMap;
    public int i;
    public final int x;
    public final int z;
    public Map tileEntities;
    public List[] entitySlices;
    public boolean done;
    public boolean o;
    public boolean p;
    public boolean q;
    public long r;

    public Chunk(World world, int i, int j) {
        super((com.legacyminecraft.poseidon.world.World) (Object) world, i, j);
        this.tileEntities = new HashMap();
        this.entitySlices = new List[8];
        this.done = false;
        this.o = false;
        this.q = false;
        this.r = 0L;
        this.world = world;
        this.x = i;
        this.z = j;
        this.heightMap = new byte[256];

        for (int k = 0; k < this.entitySlices.length; ++k) {
            this.entitySlices[k] = new ArrayList();
        }

        // CraftBukkit start
        this.bukkitChunk = (org.bukkit.Chunk) (Object) CHUNK_ENTITY_SLICE_CLEANUP_BEHAVIOUR.createBukkitChunk(this);
    }

    public org.bukkit.Chunk bukkitChunk;
    // CraftBukkit end

    public Chunk(World world, byte[] abyte, int i, int j) {
        this(world, i, j);
        this.b = abyte;
        this.e = new NibbleArray(abyte.length);
        this.f = new NibbleArray(abyte.length);
        this.g = new NibbleArray(abyte.length);
    }

    public boolean a(int i, int j) {
        return CHUNK_STATE_ACCESS_BEHAVIOUR.isChunkCoordinates(i, j, this.x, this.z);
    }

    public int b(int i, int j) {
        return CHUNK_STATE_ACCESS_BEHAVIOUR.getHeightAt(this.heightMap, i, j);
    }

    public void a() {}

    public void initLighting() {
        ChunkLightingInitializationBehaviour.InitResult initResult =
                CHUNK_LIGHTING_INITIALIZATION_BEHAVIOUR.initLighting(this.world, this.b, this.heightMap, this.f);
        this.i = initResult.minHeight();

        for (int localX = 0; localX < 16; ++localX) {
            for (int localZ = 0; localZ < 16; ++localZ) {
                this.c(localX, localZ);
            }
        }

        if (initResult.dirty()) {
            this.o = true;
        }
    }

    public void loadNOP() {}

    private void c(int i, int j) {
        CHUNK_SKY_LIGHT_COLUMN_UPDATE_BEHAVIOUR.relightNeighborColumns(
                this.world,
                this.x,
                this.z,
                i,
                j,
                this.b(i, j),
                new ChunkSkyLightColumnUpdateBehaviour.SkyLightUpdateActions() {
                    @Override
                    public void markDirty() {
                        o = true;
                    }
                }
        );
    }

    private void f(int i, int j, int k) {
        CHUNK_SKY_LIGHT_COLUMN_UPDATE_BEHAVIOUR.updateColumn(
                this.world,
                i,
                j,
                k,
                new ChunkSkyLightColumnUpdateBehaviour.SkyLightUpdateActions() {
                    @Override
                    public void markDirty() {
                        o = true;
                    }
                }
        );
    }

    private void g(int i, int j, int k) {
        ChunkHeightColumnUpdateBehaviour.UpdateResult updateResult =
                CHUNK_HEIGHT_COLUMN_UPDATE_BEHAVIOUR.updateHeightColumn(
                        this.world,
                        this.b,
                        this.heightMap,
                        this.f,
                        this.x,
                        this.z,
                        i,
                        j,
                        k,
                        this.i
                );
        this.i = updateResult.minHeight();
        if (updateResult.dirty()) {
            this.o = true;
        }
    }

    public int getTypeId(int i, int j, int k) {
        return this.b[i << 11 | k << 7 | j] & 255;
    }

    public boolean a(int i, int j, int k, int l, int i1) {
        boolean changed = CHUNK_BLOCK_MUTATION_BEHAVIOUR.setTypeAndData(
                this.world,
                this.b,
                this.heightMap,
                this.e,
                this.x,
                this.z,
                i,
                j,
                k,
                l,
                i1,
                PoseidonConfig.getInstance().getConfigBoolean(
                        WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledKey(),
                        WORLD_FEATURE_CONFIG_POLICY.pistonTransmutationFixEnabledDefault()
                ),
                new ChunkBlockMutationBehaviour.HeightUpdateActions() {
                    @Override
                    public void updateHeightColumn(int x, int y, int z) {
                        g(x, y, z);
                    }

                    @Override
                    public void relightNeighborColumns(int x, int z) {
                        c(x, z);
                    }
                }
        );
        if (changed) {
            this.o = true;
        }
        return changed;
    }

    public boolean a(int i, int j, int k, int l) {
        boolean changed = CHUNK_BLOCK_MUTATION_BEHAVIOUR.setType(
                this.world,
                this.b,
                this.heightMap,
                this.e,
                this.x,
                this.z,
                i,
                j,
                k,
                l,
                new ChunkBlockMutationBehaviour.HeightUpdateActions() {
                    @Override
                    public void updateHeightColumn(int x, int y, int z) {
                        g(x, y, z);
                    }

                    @Override
                    public void relightNeighborColumns(int x, int z) {
                        c(x, z);
                    }
                }
        );
        if (changed) {
            this.o = true;
        }
        return changed;
    }

    public int getData(int i, int j, int k) {
        return CHUNK_STATE_ACCESS_BEHAVIOUR.getMetadata(this.e, i, j, k);
    }

    public void b(int i, int j, int k, int l) {
        this.o = true;
        CHUNK_STATE_ACCESS_BEHAVIOUR.setMetadata(this.e, i, j, k, l);
    }

    public int a(EnumSkyBlock enumskyblock, int i, int j, int k) {
        return CHUNK_LIGHT_ACCESS_BEHAVIOUR.getLight(this.f, this.g, enumskyblock, i, j, k);
    }

    public void a(EnumSkyBlock enumskyblock, int i, int j, int k, int l) {
        this.o = true;
        CHUNK_LIGHT_ACCESS_BEHAVIOUR.setLight(this.f, this.g, enumskyblock, i, j, k, l);
    }

    public int c(int i, int j, int k, int l) {
        int rawSky = this.f.a(i, j, k);
        if (rawSky > 0) {
            a = true;
        }
        return CHUNK_LIGHT_ACCESS_BEHAVIOUR.resolveBrightness(this.f, this.g, i, j, k, l);
    }

    public void a(Entity entity) {
        this.q = true;
        int i = CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.resolveChunkCoordinate(entity.locX);
        int j = CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.resolveChunkCoordinate(entity.locZ);

        if (i != this.x || j != this.z) {
            System.out.println("Wrong location! " + entity);
            // Thread.dumpStack(); // CraftBukkit
            // CraftBukkit
            System.out.println("" + entity.locX + "," + entity.locZ + "(" + i + "," + j + ") vs " + this.x + "," + this.z);
        }

        int k = CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.resolveSliceIndex(entity.locY, this.entitySlices.length);
        CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.markAndAdd(entity, this.x, this.z, k, this.entitySlices);
    }

    public void b(Entity entity) {
        this.a(entity, entity.bI);
    }

    public void a(Entity entity, int i) {
        int sliceIndex = CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.clampSliceIndex(i, this.entitySlices.length);
        CHUNK_ENTITY_SLICE_INDEX_BEHAVIOUR.remove(entity, sliceIndex, this.entitySlices);
    }

    public boolean c(int i, int j, int k) {
        return CHUNK_STATE_ACCESS_BEHAVIOUR.isAtOrAboveHeight(this.heightMap, i, j, k);
    }

    public TileEntity d(int i, int j, int k) {
        ChunkPosition chunkposition = new ChunkPosition(i, j, k);
        TileEntity tileentity = CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.resolveFromCache(this.tileEntities, chunkposition);

        if (tileentity == null) {
            int l = this.getTypeId(i, j, k);

            if (!CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.shouldCreateForType(l)) {
                return null;
            }

            CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.createTileEntity(
                    Block.byId[l],
                    this.world,
                    this.x * 16 + i,
                    j,
                    this.z * 16 + k
            );
            tileentity = CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.resolveFromCache(this.tileEntities, chunkposition);
        }

        return CHUNK_TILE_ENTITY_LOOKUP_BEHAVIOUR.pruneInvalidTileEntity(this.tileEntities, chunkposition, tileentity);
    }

    public void a(TileEntity tileentity) {
        int i = tileentity.x - this.x * 16;
        int j = tileentity.y;
        int k = tileentity.z - this.z * 16;

        this.placeTileEntity(i, j, k, tileentity);
        if (this.c) {
            this.world.c.add(tileentity);
        }
    }

    public void placeTileEntity(int i, int j, int k, TileEntity tileentity) {
        CHUNK_TILE_ENTITY_LIFECYCLE_BEHAVIOUR.placeTileEntity(
                this.tileEntities,
                this.world,
                this.x,
                this.z,
                i,
                j,
                k,
                this.getTypeId(i, j, k),
                tileentity
        );
    }

    public void e(int i, int j, int k) {
        CHUNK_TILE_ENTITY_LIFECYCLE_BEHAVIOUR.removeTileEntityIfActive(this.tileEntities, this.c, i, j, k);
    }

    public void addEntities() {
        this.c = true;
        CHUNK_ENTITY_LIFECYCLE_BEHAVIOUR.addEntities(this.world, this.tileEntities, this.entitySlices);
    }

    public void removeEntities() {
        this.c = false;
        CHUNK_ENTITY_LIFECYCLE_BEHAVIOUR.removeEntities(
                this.world,
                this.tileEntities,
                this.entitySlices,
                this.x,
                this.z,
                new ChunkEntityLifecycleBehaviour.PlayerSliceRemovalPolicy() {
                    @Override
                    public boolean shouldRemove(Object entity, int chunkX, int chunkZ) {
                        // Do not pass along players outside their source chunk, as this can
                        // leave them stuck outside normal world-time updates.
                        return CHUNK_ENTITY_SLICE_CLEANUP_BEHAVIOUR.shouldRemoveCrossChunkPlayerEntity(
                                (com.legacyminecraft.compat.bukkit.Entity) entity,
                                chunkX,
                                chunkZ
                        );
                    }
                }
        );
    }

    public void f() {
        this.o = true;
    }

    public void a(Entity entity, AxisAlignedBB axisalignedbb, List list) {
        ChunkEntityQueryBehaviour.SliceBounds bounds =
                CHUNK_ENTITY_QUERY_BEHAVIOUR.resolveSliceBounds(axisalignedbb, this.entitySlices.length);
        CHUNK_ENTITY_QUERY_BEHAVIOUR.collectCollidingExcluding(entity, axisalignedbb, this.entitySlices, list, bounds);
    }

    public void a(Class oclass, AxisAlignedBB axisalignedbb, List list) {
        ChunkEntityQueryBehaviour.SliceBounds bounds =
                CHUNK_ENTITY_QUERY_BEHAVIOUR.resolveSliceBounds(axisalignedbb, this.entitySlices.length);
        CHUNK_ENTITY_QUERY_BEHAVIOUR.collectAssignableColliding(oclass, axisalignedbb, this.entitySlices, list, bounds);
    }

    public boolean a(boolean flag) {
        return CHUNK_SAVE_DECISION_BEHAVIOUR.shouldSave(this.p, this.q, this.world.getTime(), this.r, flag, this.o);
    }

    public int getData(byte[] abyte, int i, int j, int k, int l, int i1, int j1, int k1) {
        return CHUNK_DATA_EXTRACTION_BEHAVIOUR.copyChunkData(
                this.b,
                this.e.a,
                this.g.a,
                this.f.a,
                abyte,
                i,
                j,
                k,
                l,
                i1,
                j1,
                k1
        );
    }

    public Random a(long i) {
        return CHUNK_SEEDED_RANDOM_BEHAVIOUR.create(this.world.getSeed(), this.x, this.z, i);
    }

    public boolean isEmpty() {
        return false;
    }

    public void h() {
        BlockRegister.a(this.b);
    }
}
