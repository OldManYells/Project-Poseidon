package com.legacyminecraft.poseidon.block;


import com.legacyminecraft.poseidon.entity.EntityHuman;

/**
 * Canonical facing, state, and shape policy for piston block wrappers.
 */
public final class PistonStateBehaviour {
    private static final PistonStateBehaviour INSTANCE = new PistonStateBehaviour();

    private PistonStateBehaviour() {
    }

    public static PistonStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int extractFacing(int blockData) {
        return blockData & 7;
    }

    public boolean isExtended(int blockData) {
        return (blockData & 8) != 0;
    }

    public int resolveTextureIndex(int side, int blockData, int textureId, boolean fullBlockBounds) {
        int facing = extractFacing(blockData);
        if (facing > 5) {
            return textureId;
        }

        if (side == facing) {
            return !isExtended(blockData) && fullBlockBounds ? textureId : 110;
        }

        return side == PistonBlockTextures.a[facing] ? 109 : 108;
    }

    public int resolvePlacedFacing(EntityHuman entityHuman, int blockX, int blockY, int blockZ) {
        return resolvePlacedFacing(
                entityHuman.locX,
                entityHuman.locY,
                entityHuman.locZ,
                entityHuman.yaw,
                entityHuman.height,
                blockX,
                blockY,
                blockZ
        );
    }

    public int resolvePlacedFacing(
            double playerX,
            double playerY,
            double playerZ,
            float playerYaw,
            float playerHeight,
            int blockX,
            int blockY,
            int blockZ
    ) {
        if (MathHelper.abs((float) playerX - (float) blockX) < 2.0F
                && MathHelper.abs((float) playerZ - (float) blockZ) < 2.0F) {
            double eyeHeight = playerY + 1.82D - (double) playerHeight;
            if (eyeHeight - (double) blockY > 2.0D) {
                return 1;
            }

            if ((double) blockY - eyeHeight > 0.0D) {
                return 0;
            }
        }

        int horizontalFacing = MathHelper.floor((double) (playerYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return horizontalFacing == 0 ? 2 : (horizontalFacing == 1 ? 5 : (horizontalFacing == 2 ? 3 : (horizontalFacing == 3 ? 4 : 0)));
    }

    public Bounds resolveExtendedBounds(int blockData) {
        if (!isExtended(blockData)) {
            return null;
        }

        switch (extractFacing(blockData)) {
            case 0:
                return new Bounds(0.0F, 0.25F, 0.0F, 1.0F, 1.0F, 1.0F);
            case 1:
                return new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 0.75F, 1.0F);
            case 2:
                return new Bounds(0.0F, 0.0F, 0.25F, 1.0F, 1.0F, 1.0F);
            case 3:
                return new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.75F);
            case 4:
                return new Bounds(0.25F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            case 5:
                return new Bounds(0.0F, 0.0F, 0.0F, 0.75F, 1.0F, 1.0F);
            default:
                return null;
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
