package com.legacyminecraft.poseidon.entity;

import java.util.Collections;
import java.util.List;

/**
 * Entity-local world alias for migrated entity behaviours.
 */
public class World extends com.legacyminecraft.poseidon.world.World {
    public int spawnMonsters;
    public int n;
    public final java.util.List players = new java.util.ArrayList();
    private final WorldChunkManager worldChunkManager = new WorldChunkManager();

    public boolean areChunksLoaded(int x, int y, int z, int radius) {
        return true;
    }

    public void makeSound(double x, double y, double z, String sound, float volume, float pitch) {
    }

    public void makeSound(Entity source, String sound, float volume, float pitch) {
    }

    public List b(Entity entity, AxisAlignedBB bounds) {
        return Collections.emptyList();
    }

    public List getEntities(Entity entity, AxisAlignedBB bounds) {
        return Collections.emptyList();
    }

    public com.legacyminecraft.compat.bukkit.CraftServer getServer() {
        return new com.legacyminecraft.compat.bukkit.CraftServer();
    }

    public ChunkCoordinates getSpawn() {
        return new ChunkCoordinates(0, 64, 0);
    }

    public EntityHuman a(String ownerName) {
        return null;
    }

    public int a(Class entityClass) {
        return 0;
    }

    public Material getMaterial(int x, int y, int z) {
        return Material.AIR;
    }

    public boolean a(AxisAlignedBB bounds, Material material, Entity entity) {
        return false;
    }

    public boolean a(AxisAlignedBB bounds, Material material) {
        return false;
    }

    public boolean c(AxisAlignedBB bounds) {
        return false;
    }

    public boolean s(int x, int y, int z) {
        return false;
    }

    public float n(int x, int y, int z) {
        return 0.0F;
    }

    public boolean e(int x, int y, int z) {
        return false;
    }

    public EntityHuman a(float x, float y, float z, double range) {
        return null;
    }

    public boolean a(int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
        return false;
    }

    public Entity findNearbyPlayer(Entity source, double range) {
        return null;
    }

    public PathEntity findPath(Entity source, Entity target, float range) {
        if (!(source instanceof EntityLiving)) {
            return null;
        }
        return new Pathfinder(this).a((EntityLiving) source, target, range);
    }

    public void addEntity(EntityLiving entity, SpawnReason reason) {
    }

    public void addEntity(Entity entity) {
    }

    public boolean isChunkLoaded(int x, int y, int z) {
        return true;
    }

    public boolean d() {
        return false;
    }

    public void createExplosion(Entity source, double x, double y, double z, float radius, boolean fire) {
    }

    public void a(Entity source, byte status) {
    }

    public void a(String particle, double x, double y, double z, double velocityX, double velocityY, double velocityZ) {
    }

    public java.util.List a(Class entityClass, AxisAlignedBB bounds) {
        return Collections.emptyList();
    }

    public MovingObjectPosition rayTrace(Vec3D start, Vec3D end, boolean includeFluids) {
        return null;
    }

    public boolean a(int blockId, int x, int y, int z, boolean canReplace, int face) {
        return true;
    }

    public boolean setRawTypeId(int x, int y, int z, int typeId) {
        return true;
    }

    @Override
    public WorldChunkManager getWorldChunkManager() {
        return worldChunkManager;
    }
}
