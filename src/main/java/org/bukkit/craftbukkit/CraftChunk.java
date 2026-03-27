package org.bukkit.craftbukkit;

import com.legacyminecraft.compat.bukkit.CraftChunkAccessBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkEmptySnapshotBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkEmptySnapshotFactoryBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkHandleResolutionBehaviour;
import com.legacyminecraft.compat.bukkit.ChunkSnapshotCaptureBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkLifecycleBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkInitializationBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkIdentityBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkWeakLinkBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkSnapshotModeBehaviour;
import com.legacyminecraft.compat.bukkit.CraftChunkSnapshotSystem;
import com.google.common.collect.MapMaker;
import net.minecraft.server.WorldServer;
import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import java.lang.ref.WeakReference;
import java.util.concurrent.ConcurrentMap;

public class CraftChunk implements Chunk {
    private static final CraftChunkAccessBehaviour CRAFT_CHUNK_ACCESS_BEHAVIOUR =
            CraftChunkAccessBehaviour.getInstance();
    private static final CraftChunkHandleResolutionBehaviour CRAFT_CHUNK_HANDLE_RESOLUTION_BEHAVIOUR =
            CraftChunkHandleResolutionBehaviour.getInstance();
    private static final CraftChunkEmptySnapshotBehaviour CRAFT_CHUNK_EMPTY_SNAPSHOT_BEHAVIOUR =
            CraftChunkEmptySnapshotBehaviour.getInstance();
    private static final CraftChunkEmptySnapshotFactoryBehaviour CRAFT_CHUNK_EMPTY_SNAPSHOT_FACTORY_BEHAVIOUR =
            CraftChunkEmptySnapshotFactoryBehaviour.getInstance();
    private static final CraftChunkLifecycleBehaviour CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR =
            CraftChunkLifecycleBehaviour.getInstance();
    private static final CraftChunkInitializationBehaviour CRAFT_CHUNK_INITIALIZATION_BEHAVIOUR =
            CraftChunkInitializationBehaviour.getInstance();
    private static final CraftChunkIdentityBehaviour CRAFT_CHUNK_IDENTITY_BEHAVIOUR =
            CraftChunkIdentityBehaviour.getInstance();
    private static final CraftChunkWeakLinkBehaviour CRAFT_CHUNK_WEAK_LINK_BEHAVIOUR =
            CraftChunkWeakLinkBehaviour.getInstance();
    private static final CraftChunkSnapshotModeBehaviour CRAFT_CHUNK_SNAPSHOT_MODE_BEHAVIOUR =
            CraftChunkSnapshotModeBehaviour.getInstance();
    private static final CraftChunkSnapshotSystem CRAFT_CHUNK_SNAPSHOT_SYSTEM =
            CraftChunkSnapshotSystem.getInstance();
    private WeakReference<net.minecraft.server.Chunk> weakChunk;
    private final ConcurrentMap<Integer, Block> cache = new MapMaker().softValues().makeMap();
    private WorldServer worldServer;
    private int x;
    private int z;

    public CraftChunk(net.minecraft.server.Chunk chunk) {
        this.weakChunk = new WeakReference<net.minecraft.server.Chunk>(chunk);
        CraftChunkInitializationBehaviour.InitializationState initializationState =
                CRAFT_CHUNK_INITIALIZATION_BEHAVIOUR.initialize(getHandle());
        worldServer = initializationState.getWorldServer();
        x = initializationState.getX();
        z = initializationState.getZ();
    }

    public World getWorld() {
        return CRAFT_CHUNK_IDENTITY_BEHAVIOUR.getWorld(worldServer);
    }

    public net.minecraft.server.Chunk getHandle() {
        CraftChunkHandleResolutionBehaviour.ResolutionResult resolutionResult =
                CRAFT_CHUNK_HANDLE_RESOLUTION_BEHAVIOUR.resolveHandle(weakChunk, worldServer, x, z);
        weakChunk = resolutionResult.getRefreshedWeakChunk();
        return resolutionResult.getChunk();
    }

    void breakLink() {
        CRAFT_CHUNK_WEAK_LINK_BEHAVIOUR.breakLink(weakChunk);
    }

    public int getX() {
        return CRAFT_CHUNK_IDENTITY_BEHAVIOUR.getX(x);
    }

    public int getZ() {
        return CRAFT_CHUNK_IDENTITY_BEHAVIOUR.getZ(z);
    }

    @Override
    public String toString() {
        return CRAFT_CHUNK_IDENTITY_BEHAVIOUR.toString(getX(), getZ());
    }

    public Block getBlock(int x, int y, int z) {
        return CRAFT_CHUNK_ACCESS_BEHAVIOUR.resolveBlock(this.cache, this, getX(), getZ(), x, y, z);
    }

    public Entity[] getEntities() {
        return CRAFT_CHUNK_ACCESS_BEHAVIOUR.collectEntities(getHandle());
    }

    public BlockState[] getTileEntities() {
        return CRAFT_CHUNK_ACCESS_BEHAVIOUR.collectTileEntityStates(getHandle(), worldServer);
    }

    public boolean isLoaded() {
        return CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR.isLoaded(getWorld(), getX(), getZ());
    }

    public boolean load() {
        return CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR.load(getWorld(), getX(), getZ(), true);
    }

    public boolean load(boolean generate) {
        return CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR.load(getWorld(), getX(), getZ(), generate);
    }

    public boolean unload() {
        return CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR.unload(getWorld(), getX(), getZ());
    }

    public boolean unload(boolean save) {
        return CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR.unload(getWorld(), getX(), getZ(), save);
    }

    public boolean unload(boolean save, boolean safe) {
        return CRAFT_CHUNK_LIFECYCLE_BEHAVIOUR.unload(getWorld(), getX(), getZ(), save, safe);
    }

    public ChunkSnapshot getChunkSnapshot() {
        return getChunkSnapshot(
                CRAFT_CHUNK_SNAPSHOT_MODE_BEHAVIOUR.includeMaxBlockYByDefault(),
                CRAFT_CHUNK_SNAPSHOT_MODE_BEHAVIOUR.includeBiomeByDefault(),
                CRAFT_CHUNK_SNAPSHOT_MODE_BEHAVIOUR.includeBiomeClimateByDefault()
        );
    }

    public ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain) {
        return CRAFT_CHUNK_SNAPSHOT_SYSTEM.createSnapshot(
                this,
                includeMaxblocky,
                includeBiome,
                includeBiomeTempRain
        );
    }

    public static ChunkSnapshot createEmptyChunkSnapshot(
            int x,
            int z,
            CraftWorld world,
            ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData
    ) {
        return CRAFT_CHUNK_EMPTY_SNAPSHOT_FACTORY_BEHAVIOUR.createEmptyChunkSnapshot(
                x,
                z,
                world,
                snapshotData
        );
    }

    public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
        return CRAFT_CHUNK_EMPTY_SNAPSHOT_BEHAVIOUR.createEmptySnapshot(world, x, z, includeBiome, includeBiomeTempRain);
    }
}
