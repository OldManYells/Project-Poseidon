package com.legacyminecraft.poseidon.block;


/**
 * Canonical geometry and placement rules for legacy stair block wrappers.
 */
public final class StairGeometryBehaviour {
    private static final StairGeometryBehaviour INSTANCE = new StairGeometryBehaviour();

    private StairGeometryBehaviour() {
    }

    public static StairGeometryBehaviour getInstance() {
        return INSTANCE;
    }

    public CollisionShapePair resolveCollisionShapes(int metadata) {
        switch (metadata) {
            case 0:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.0F, 0.5F, 0.5F, 1.0F),
                        new Bounds(0.5F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F)
                );
            case 1:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, 1.0F),
                        new Bounds(0.5F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F)
                );
            case 2:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 0.5F),
                        new Bounds(0.0F, 0.0F, 0.5F, 1.0F, 1.0F, 1.0F)
                );
            case 3:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.5F),
                        new Bounds(0.0F, 0.0F, 0.5F, 1.0F, 0.5F, 1.0F)
                );
            default:
                return null;
        }
    }

    public int resolvePlacementMetadata(float yaw) {
        int facing = MathHelper.floor((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3;
        switch (facing) {
            case 0:
                return 2;
            case 1:
                return 1;
            case 2:
                return 3;
            case 3:
                return 0;
            default:
                return 0;
        }
    }

    public static final class CollisionShapePair {
        private final Bounds primary;
        private final Bounds secondary;

        public CollisionShapePair(Bounds primary, Bounds secondary) {
            this.primary = primary;
            this.secondary = secondary;
        }

        public Bounds getPrimary() {
            return primary;
        }

        public Bounds getSecondary() {
            return secondary;
        }
    }

    public static final class Bounds {
        private final float minX;
        private final float minY;
        private final float minZ;
        private final float maxX;
        private final float maxY;
        private final float maxZ;

        public Bounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }

        public float getMinX() {
            return minX;
        }

        public float getMinY() {
            return minY;
        }

        public float getMinZ() {
            return minZ;
        }

        public float getMaxX() {
            return maxX;
        }

        public float getMaxY() {
            return maxY;
        }

        public float getMaxZ() {
            return maxZ;
        }
    }
}
