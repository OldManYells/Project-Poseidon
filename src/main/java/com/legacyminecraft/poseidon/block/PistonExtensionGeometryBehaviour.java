package com.legacyminecraft.poseidon.block;


/**
 * Canonical geometry and texture policy for legacy piston-extension wrappers.
 */
public final class PistonExtensionGeometryBehaviour {
    private static final PistonExtensionGeometryBehaviour INSTANCE = new PistonExtensionGeometryBehaviour();

    private PistonExtensionGeometryBehaviour() {
    }

    public static PistonExtensionGeometryBehaviour getInstance() {
        return INSTANCE;
    }

    public int extractFacing(int blockData) {
        return blockData & 7;
    }

    public int resolveTextureIndex(int side, int blockData, int overrideTexture, int textureId) {
        int facing = extractFacing(blockData);
        return side == facing
                ? (overrideTexture >= 0 ? overrideTexture : ((blockData & 8) != 0 ? textureId - 1 : textureId))
                : (side == PistonBlockTextures.a[facing] ? 107 : 108);
    }

    public CollisionShapePair resolveCollisionShapes(int blockData) {
        int facing = extractFacing(blockData);

        switch (facing) {
            case 0:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F),
                        new Bounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F)
                );
            case 1:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F),
                        new Bounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F)
                );
            case 2:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F),
                        new Bounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F)
                );
            case 3:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F),
                        new Bounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F)
                );
            case 4:
                return new CollisionShapePair(
                        new Bounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F),
                        new Bounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F)
                );
            case 5:
                return new CollisionShapePair(
                        new Bounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F),
                        new Bounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F)
                );
            default:
                return null;
        }
    }

    public Bounds resolveOutlineShape(int blockData) {
        int facing = extractFacing(blockData);

        switch (facing) {
            case 0:
                return new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
            case 1:
                return new Bounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
            case 2:
                return new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
            case 3:
                return new Bounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
            case 4:
                return new Bounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
            case 5:
                return new Bounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            default:
                return null;
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
