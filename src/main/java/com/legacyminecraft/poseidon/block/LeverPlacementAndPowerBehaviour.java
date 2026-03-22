package com.legacyminecraft.poseidon.block;

/**
 * Canonical placement, orientation, and power policy for legacy lever wrappers.
 */
public final class LeverPlacementAndPowerBehaviour {
    private static final LeverPlacementAndPowerBehaviour INSTANCE = new LeverPlacementAndPowerBehaviour();

    private LeverPlacementAndPowerBehaviour() {
    }

    public static LeverPlacementAndPowerBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canPlaceOnSide(SupportQuery supportQuery, int x, int y, int z, int side) {
        return side == 1 && supportQuery.isBlockSolid(x, y - 1, z)
                || side == 2 && supportQuery.isBlockSolid(x, y, z + 1)
                || side == 3 && supportQuery.isBlockSolid(x, y, z - 1)
                || side == 4 && supportQuery.isBlockSolid(x + 1, y, z)
                || side == 5 && supportQuery.isBlockSolid(x - 1, y, z);
    }

    public boolean canPlace(SupportQuery supportQuery, int x, int y, int z) {
        return supportQuery.isBlockSolid(x - 1, y, z)
                || supportQuery.isBlockSolid(x + 1, y, z)
                || supportQuery.isBlockSolid(x, y, z - 1)
                || supportQuery.isBlockSolid(x, y, z + 1)
                || supportQuery.isBlockSolid(x, y - 1, z);
    }

    public int resolvePostPlaceData(
            SupportQuery supportQuery,
            RandomSource randomSource,
            int x,
            int y,
            int z,
            int clickedSide,
            int currentData
    ) {
        int poweredBit = currentData & 8;
        int orientation = -1;

        if (clickedSide == 1 && supportQuery.isBlockSolid(x, y - 1, z)) {
            orientation = 5 + randomSource.nextInt(2);
        }

        if (clickedSide == 2 && supportQuery.isBlockSolid(x, y, z + 1)) {
            orientation = 4;
        }

        if (clickedSide == 3 && supportQuery.isBlockSolid(x, y, z - 1)) {
            orientation = 3;
        }

        if (clickedSide == 4 && supportQuery.isBlockSolid(x + 1, y, z)) {
            orientation = 2;
        }

        if (clickedSide == 5 && supportQuery.isBlockSolid(x - 1, y, z)) {
            orientation = 1;
        }

        return orientation == -1 ? -1 : composeData(orientation, poweredBit);
    }

    public boolean isAttachedSupportMissing(SupportQuery supportQuery, int x, int y, int z, int blockData) {
        int orientation = extractOrientation(blockData);
        return orientation == 1 && !supportQuery.isBlockSolid(x - 1, y, z)
                || orientation == 2 && !supportQuery.isBlockSolid(x + 1, y, z)
                || orientation == 3 && !supportQuery.isBlockSolid(x, y, z - 1)
                || orientation == 4 && !supportQuery.isBlockSolid(x, y, z + 1)
                || orientation == 5 && !supportQuery.isBlockSolid(x, y - 1, z)
                || orientation == 6 && !supportQuery.isBlockSolid(x, y - 1, z);
    }

    public Bounds resolveBounds(int blockData) {
        int orientation = extractOrientation(blockData);
        float sideRadius = 0.1875F;

        if (orientation == 1) {
            return new Bounds(0.0F, 0.2F, 0.5F - sideRadius, sideRadius * 2.0F, 0.8F, 0.5F + sideRadius);
        }
        if (orientation == 2) {
            return new Bounds(1.0F - sideRadius * 2.0F, 0.2F, 0.5F - sideRadius, 1.0F, 0.8F, 0.5F + sideRadius);
        }
        if (orientation == 3) {
            return new Bounds(0.5F - sideRadius, 0.2F, 0.0F, 0.5F + sideRadius, 0.8F, sideRadius * 2.0F);
        }
        if (orientation == 4) {
            return new Bounds(0.5F - sideRadius, 0.2F, 1.0F - sideRadius * 2.0F, 0.5F + sideRadius, 0.8F, 1.0F);
        }

        float topRadius = 0.25F;
        return new Bounds(0.5F - topRadius, 0.0F, 0.5F - topRadius, 0.5F + topRadius, 0.6F, 0.5F + topRadius);
    }

    public int extractOrientation(int blockData) {
        return blockData & 7;
    }

    public boolean isPowered(int blockData) {
        return (blockData & 8) > 0;
    }

    public int calculateTogglePowerBit(int blockData) {
        return 8 - (blockData & 8);
    }

    public int composeData(int orientation, int powerBit) {
        return orientation + powerBit;
    }

    public NeighborOffset resolveAttachmentOffset(int orientation) {
        if (orientation == 1) {
            return new NeighborOffset(-1, 0, 0);
        }
        if (orientation == 2) {
            return new NeighborOffset(1, 0, 0);
        }
        if (orientation == 3) {
            return new NeighborOffset(0, 0, -1);
        }
        if (orientation == 4) {
            return new NeighborOffset(0, 0, 1);
        }
        return new NeighborOffset(0, -1, 0);
    }

    public boolean isPoweringSide(int blockData, int side) {
        if (!isPowered(blockData)) {
            return false;
        }

        int orientation = extractOrientation(blockData);
        return orientation == 6 && side == 1
                || orientation == 5 && side == 1
                || orientation == 4 && side == 2
                || orientation == 3 && side == 3
                || orientation == 2 && side == 4
                || orientation == 1 && side == 5;
    }

    public interface SupportQuery {
        boolean isBlockSolid(int x, int y, int z);
    }

    public interface RandomSource {
        int nextInt(int bound);
    }

    public static final class NeighborOffset {
        private final int x;
        private final int y;
        private final int z;

        public NeighborOffset(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getZ() {
            return z;
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
