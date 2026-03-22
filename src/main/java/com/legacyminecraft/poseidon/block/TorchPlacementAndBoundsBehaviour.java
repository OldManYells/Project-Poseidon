package com.legacyminecraft.poseidon.block;

import net.minecraft.server.Block;

/**
 * Canonical placement, attachment, and bounds policy for legacy torch wrappers.
 */
public final class TorchPlacementAndBoundsBehaviour {
    private static final TorchPlacementAndBoundsBehaviour INSTANCE = new TorchPlacementAndBoundsBehaviour();

    private TorchPlacementAndBoundsBehaviour() {
    }

    public static TorchPlacementAndBoundsBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean hasFloorSupport(SupportQuery supportQuery, int x, int y, int z) {
        return supportQuery.isBlockSolid(x, y, z) || supportQuery.getTypeId(x, y, z) == Block.FENCE.id;
    }

    public boolean canPlace(SupportQuery supportQuery, int x, int y, int z) {
        return supportQuery.isBlockSolid(x - 1, y, z)
                || supportQuery.isBlockSolid(x + 1, y, z)
                || supportQuery.isBlockSolid(x, y, z - 1)
                || supportQuery.isBlockSolid(x, y, z + 1)
                || hasFloorSupport(supportQuery, x, y - 1, z);
    }

    public int resolvePostPlaceData(SupportQuery supportQuery, int currentData, int clickedSide, int x, int y, int z) {
        int resolvedData = currentData;
        if (clickedSide == 1 && hasFloorSupport(supportQuery, x, y - 1, z)) {
            resolvedData = 5;
        }

        if (clickedSide == 2 && supportQuery.isBlockSolid(x, y, z + 1)) {
            resolvedData = 4;
        }

        if (clickedSide == 3 && supportQuery.isBlockSolid(x, y, z - 1)) {
            resolvedData = 3;
        }

        if (clickedSide == 4 && supportQuery.isBlockSolid(x + 1, y, z)) {
            resolvedData = 2;
        }

        if (clickedSide == 5 && supportQuery.isBlockSolid(x - 1, y, z)) {
            resolvedData = 1;
        }

        return resolvedData;
    }

    public int resolveFallbackData(SupportQuery supportQuery, int x, int y, int z) {
        if (supportQuery.isBlockSolid(x - 1, y, z)) {
            return 1;
        } else if (supportQuery.isBlockSolid(x + 1, y, z)) {
            return 2;
        } else if (supportQuery.isBlockSolid(x, y, z - 1)) {
            return 3;
        } else if (supportQuery.isBlockSolid(x, y, z + 1)) {
            return 4;
        } else if (hasFloorSupport(supportQuery, x, y - 1, z)) {
            return 5;
        }
        return 0;
    }

    public boolean isAttachmentMissing(SupportQuery supportQuery, int x, int y, int z, int blockData) {
        return blockData == 1 && !supportQuery.isBlockSolid(x - 1, y, z)
                || blockData == 2 && !supportQuery.isBlockSolid(x + 1, y, z)
                || blockData == 3 && !supportQuery.isBlockSolid(x, y, z - 1)
                || blockData == 4 && !supportQuery.isBlockSolid(x, y, z + 1)
                || blockData == 5 && !hasFloorSupport(supportQuery, x, y - 1, z);
    }

    public Bounds resolveRaytraceBounds(int blockData) {
        int orientation = blockData & 7;
        float sideRadius = 0.15F;

        if (orientation == 1) {
            return new Bounds(0.0F, 0.2F, 0.5F - sideRadius, sideRadius * 2.0F, 0.8F, 0.5F + sideRadius);
        } else if (orientation == 2) {
            return new Bounds(1.0F - sideRadius * 2.0F, 0.2F, 0.5F - sideRadius, 1.0F, 0.8F, 0.5F + sideRadius);
        } else if (orientation == 3) {
            return new Bounds(0.5F - sideRadius, 0.2F, 0.0F, 0.5F + sideRadius, 0.8F, sideRadius * 2.0F);
        } else if (orientation == 4) {
            return new Bounds(0.5F - sideRadius, 0.2F, 1.0F - sideRadius * 2.0F, 0.5F + sideRadius, 0.8F, 1.0F);
        }

        float topRadius = 0.1F;
        return new Bounds(0.5F - topRadius, 0.0F, 0.5F - topRadius, 0.5F + topRadius, 0.6F, 0.5F + topRadius);
    }

    public boolean shouldDropForInvalidPlacement(boolean canPlace, boolean otherFixEnabled, boolean currentBlockIsTorch) {
        return !canPlace && (!otherFixEnabled || currentBlockIsTorch);
    }

    public interface SupportQuery {
        boolean isBlockSolid(int x, int y, int z);

        int getTypeId(int x, int y, int z);
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
