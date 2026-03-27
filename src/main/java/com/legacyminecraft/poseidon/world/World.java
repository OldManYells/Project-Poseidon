package com.legacyminecraft.poseidon.world;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Canonical world scaffold for migrated behaviour classes.
 */
public class World {
    public boolean isStatic;
    public final Random random = new Random();
    public WorldProvider worldProvider = new WorldProvider();
    public IChunkProvider chunkProvider;
    public final Map<Object, Float> explosionDensityCache = new HashMap<Object, Float>();
    private final WorldChunkManager worldChunkManager = new WorldChunkManager();
    private long seed;

    public int getTypeId(int x, int y, int z) {
        return 0;
    }

    public boolean isEmpty(int x, int y, int z) {
        return getTypeId(x, y, z) == 0;
    }

    public int getHighestBlockYAt(int x, int z) {
        return 0;
    }

    public int getData(int x, int y, int z) {
        return 0;
    }

    public boolean setTypeId(int x, int y, int z, int typeId) {
        return false;
    }

    public boolean setTypeIdAndData(int x, int y, int z, int typeId, int data) {
        return false;
    }

    public TileEntity getTileEntity(int x, int y, int z) {
        return null;
    }

    public void o(int x, int y, int z) {
    }

    public void markForRemoval(TileEntity tileEntity) {
    }

    public double a(Vec3D origin, AxisAlignedBB bounds) {
        return 0.0D;
    }

    public List b(Entity entity, AxisAlignedBB bounds) {
        return Collections.emptyList();
    }

    public List getEntities(Entity entity, AxisAlignedBB bounds) {
        return Collections.emptyList();
    }

    public Chunk getChunkAtWorldCoords(int x, int z) {
        return new Chunk(this);
    }

    public Chunk getChunkAt(int chunkX, int chunkZ) {
        return new Chunk(this, chunkX, chunkZ);
    }

    public int b(String key) {
        return 0;
    }

    public int a(int x, int z) {
        return 0;
    }

    public boolean a(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return true;
    }

    public Object a(Class type, String key) {
        return null;
    }

    public void a(String key, WorldMapBase value) {
    }

    public void a(EnumSkyBlock skyBlock, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
    }

    public void g(int x, int z, int newHeight, int oldHeight) {
    }

    public void a(List entities) {
    }

    public void b(List entities) {
    }

    public Object q() {
        return this.worldProvider;
    }

    public long getSeed() {
        return seed;
    }

    public void k() {
    }

    public boolean setRawData(int x, int y, int z, int data) {
        return false;
    }

    public boolean setRawTypeId(int x, int y, int z, int typeId) {
        return false;
    }

    public boolean setRawTypeIdAndData(int x, int y, int z, int typeId, int data) {
        return false;
    }

    public void notify(int x, int y, int z) {
    }

    public void applyPhysics(int x, int y, int z, int typeId) {
    }

    public boolean m(int x, int y, int z) {
        return false;
    }

    public Object getWorldChunkManager() {
        return this.worldChunkManager;
    }

    public Object getServer() {
        return new com.legacyminecraft.compat.bukkit.CraftServer();
    }

    public Object getWorld() {
        return new com.legacyminecraft.compat.bukkit.CraftWorld();
    }
}
