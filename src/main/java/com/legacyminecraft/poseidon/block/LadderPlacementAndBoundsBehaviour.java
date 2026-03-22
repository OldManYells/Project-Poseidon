package com.legacyminecraft.poseidon.block;

/**
 * Canonical placement/attachment/bounds rules for legacy ladder wrappers.
 */
public final class LadderPlacementAndBoundsBehaviour {
    private static final LadderPlacementAndBoundsBehaviour INSTANCE = new LadderPlacementAndBoundsBehaviour();

    private LadderPlacementAndBoundsBehaviour() {
    }

    public static LadderPlacementAndBoundsBehaviour getInstance() {
        return INSTANCE;
    }

    public Bounds resolveBounds(int blockData, float thickness) {
        if (blockData == 2) {
            return new Bounds(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F);
        }
        if (blockData == 3) {
            return new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness);
        }
        if (blockData == 4) {
            return new Bounds(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        if (blockData == 5) {
            return new Bounds(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F);
        }
        return null;
    }

    public boolean canPlace(SupportQuery supportQuery, int x, int y, int z) {
        return supportQuery.isBlockSolid(x - 1, y, z)
                || supportQuery.isBlockSolid(x + 1, y, z)
                || supportQuery.isBlockSolid(x, y, z - 1)
                || supportQuery.isBlockSolid(x, y, z + 1);
    }

    public int resolvePostPlaceData(SupportQuery supportQuery, int currentData, int clickedSide, int x, int y, int z) {
        int resolvedData = currentData;
        if ((resolvedData == 0 || clickedSide == 2) && supportQuery.isBlockSolid(x, y, z + 1)) {
            resolvedData = 2;
        }

        if ((resolvedData == 0 || clickedSide == 3) && supportQuery.isBlockSolid(x, y, z - 1)) {
            resolvedData = 3;
        }

        if ((resolvedData == 0 || clickedSide == 4) && supportQuery.isBlockSolid(x + 1, y, z)) {
            resolvedData = 4;
        }

        if ((resolvedData == 0 || clickedSide == 5) && supportQuery.isBlockSolid(x - 1, y, z)) {
            resolvedData = 5;
        }

        return resolvedData;
    }

    public boolean hasValidAttachment(SupportQuery supportQuery, int x, int y, int z, int blockData) {
        return blockData == 2 && supportQuery.isBlockSolid(x, y, z + 1)
                || blockData == 3 && supportQuery.isBlockSolid(x, y, z - 1)
                || blockData == 4 && supportQuery.isBlockSolid(x + 1, y, z)
                || blockData == 5 && supportQuery.isBlockSolid(x - 1, y, z);
    }

    public interface SupportQuery {
        boolean isBlockSolid(int x, int y, int z);
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
