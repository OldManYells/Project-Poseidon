package com.legacyminecraft.poseidon.world;

import net.minecraft.server.AxisAlignedBB;
import net.minecraft.server.Vec3D;
import net.minecraft.server.World;

/**
 * Canonical behaviour for explosion block-density cache keying and lookup.
 */
public final class ExplosionDensityCacheBehaviour {
    private static final ExplosionDensityCacheBehaviour INSTANCE = new ExplosionDensityCacheBehaviour();

    private ExplosionDensityCacheBehaviour() {
    }

    public static ExplosionDensityCacheBehaviour getInstance() {
        return INSTANCE;
    }

    public float getOrComputeDensity(World world,
                                     Vec3D explosionCenter,
                                     double explosionX,
                                     double explosionY,
                                     double explosionZ,
                                     AxisAlignedBB targetBounds) {
        CacheKey key = new CacheKey(world, explosionX, explosionY, explosionZ, targetBounds);
        Float blockDensity = world.explosionDensityCache.get(key);
        if (blockDensity == null) {
            blockDensity = world.a(explosionCenter, targetBounds);
            world.explosionDensityCache.put(key, blockDensity);
        }

        return blockDensity.floatValue();
    }

    public static final class CacheKey {
        private final World world;
        private final double posX;
        private final double posY;
        private final double posZ;
        private final double minX;
        private final double minY;
        private final double minZ;
        private final double maxX;
        private final double maxY;
        private final double maxZ;

        public CacheKey(World world, double posX, double posY, double posZ, AxisAlignedBB bounds) {
            this.world = world;
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
            this.minX = bounds.a;
            this.minY = bounds.b;
            this.minZ = bounds.c;
            this.maxX = bounds.d;
            this.maxY = bounds.e;
            this.maxZ = bounds.f;
        }

        @Override
        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (other == null || getClass() != other.getClass()) {
                return false;
            }

            CacheKey cacheKey = (CacheKey) other;
            if (Double.compare(cacheKey.posX, posX) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.posY, posY) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.posZ, posZ) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.minX, minX) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.minY, minY) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.minZ, minZ) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.maxX, maxX) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.maxY, maxY) != 0) {
                return false;
            }
            if (Double.compare(cacheKey.maxZ, maxZ) != 0) {
                return false;
            }
            return world.equals(cacheKey.world);
        }

        @Override
        public int hashCode() {
            int result = world.hashCode();
            long temp = Double.doubleToLongBits(posX);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(posY);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(posZ);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(minX);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(minY);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(minZ);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(maxX);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(maxY);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(maxZ);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }
    }
}
