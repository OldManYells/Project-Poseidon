package org.bukkit.craftbukkit;

import com.legacyminecraft.poseidon.compat.bukkit.CraftChunkAccessBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.CraftChunkHandleResolutionBehaviour;
import com.legacyminecraft.poseidon.compat.bukkit.ChunkSnapshotCaptureBehaviour;
import com.google.common.collect.MapMaker;
import net.minecraft.server.BiomeBase;
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
    private static final ChunkSnapshotCaptureBehaviour CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR =
            ChunkSnapshotCaptureBehaviour.getInstance();
    private WeakReference<net.minecraft.server.Chunk> weakChunk;
    private final ConcurrentMap<Integer, Block> cache = new MapMaker().softValues().makeMap();
    private WorldServer worldServer;
    private int x;
    private int z;

    public CraftChunk(net.minecraft.server.Chunk chunk) {
        this.weakChunk = new WeakReference<net.minecraft.server.Chunk>(chunk);
        worldServer = (WorldServer) getHandle().world;
        x = getHandle().x;
        z = getHandle().z;
    }

    public World getWorld() {
        return worldServer.getWorld();
    }

    public net.minecraft.server.Chunk getHandle() {
        CraftChunkHandleResolutionBehaviour.ResolutionResult resolutionResult =
                CRAFT_CHUNK_HANDLE_RESOLUTION_BEHAVIOUR.resolveHandle(weakChunk, worldServer, x, z);
        weakChunk = resolutionResult.getRefreshedWeakChunk();
        return resolutionResult.getChunk();
    }

    void breakLink() {
        weakChunk.clear();
    }

    public int getX() {
        return x;
    }

    public int getZ() {
        return z;
    }

    @Override
    public String toString() {
        return "CraftChunk{" + "x=" + getX() + "z=" + getZ() + '}';
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
        return getWorld().isChunkLoaded(this);
    }

    public boolean load() {
        return getWorld().loadChunk(getX(), getZ(), true);
    }

    public boolean load(boolean generate) {
        return getWorld().loadChunk(getX(), getZ(), generate);
    }

    public boolean unload() {
        return getWorld().unloadChunk(getX(), getZ());
    }

    public boolean unload(boolean save) {
        return getWorld().unloadChunk(getX(), getZ(), save);
    }

    public boolean unload(boolean save, boolean safe) {
        return getWorld().unloadChunk(getX(), getZ(), save, safe);
    }

    public ChunkSnapshot getChunkSnapshot() {
        return getChunkSnapshot(true, false, false);
    }

    public ChunkSnapshot getChunkSnapshot(boolean includeMaxblocky, boolean includeBiome, boolean includeBiomeTempRain) {
        net.minecraft.server.Chunk chunk = getHandle();
        ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData = CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR.captureFromChunk(
                chunk,
                getX(),
                getZ(),
                includeMaxblocky,
                includeBiome,
                includeBiomeTempRain
        );
        World world = getWorld();
        return new CraftChunkSnapshot(
                getX(),
                getZ(),
                world.getName(),
                world.getFullTime(),
                snapshotData.getChunkBuffer(),
                snapshotData.getHeightMap(),
                snapshotData.getBiomes(),
                snapshotData.getTemperatures(),
                snapshotData.getRainfall()
        );
    }

    /**
     * Empty chunk snapshot - nothing but air blocks, but can include valid biome data
     */
    private static class EmptyChunkSnapshot extends CraftChunkSnapshot {
        EmptyChunkSnapshot(int x, int z, String worldName, long time, BiomeBase[] biome, double[] biomeTemp, double[] biomeRain) {
            super(x, z, worldName, time, null, null, biome, biomeTemp, biomeRain);
        }

        public final int getBlockTypeId(int x, int y, int z) {
            return 0;
        }

        public final int getBlockData(int x, int y, int z) {
            return 0;
        }

        public final int getBlockSkyLight(int x, int y, int z) {
            return 15;
        }

        public final int getBlockEmittedLight(int x, int y, int z) {
            return 0;
        }

        public final int getHighestBlockYAt(int x, int z) {
            return 0;
        }
    }

    public static ChunkSnapshot getEmptyChunkSnapshot(int x, int z, CraftWorld world, boolean includeBiome, boolean includeBiomeTempRain) {
        ChunkSnapshotCaptureBehaviour.SnapshotData snapshotData = CHUNK_SNAPSHOT_CAPTURE_BEHAVIOUR.captureEmpty(
                world.getHandle().getWorldChunkManager(),
                x,
                z,
                includeBiome,
                includeBiomeTempRain
        );
        return new EmptyChunkSnapshot(
                x,
                z,
                world.getName(),
                world.getFullTime(),
                snapshotData.getBiomes(),
                snapshotData.getTemperatures(),
                snapshotData.getRainfall()
        );
    }
}
