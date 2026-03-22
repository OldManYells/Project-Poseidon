package com.legacyminecraft.poseidon.block;

/**
 * Canonical placement, orientation, and power-shape policy for legacy button wrappers.
 */
public final class ButtonPlacementAndPowerBehaviour {
    private static final ButtonPlacementAndPowerBehaviour INSTANCE = new ButtonPlacementAndPowerBehaviour();

    private ButtonPlacementAndPowerBehaviour() {
    }

    public static ButtonPlacementAndPowerBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean canPlaceOnSide(SupportQuery supportQuery, int x, int y, int z, int side) {
        return side == 2 && supportQuery.isBlockSolid(x, y, z + 1)
                || side == 3 && supportQuery.isBlockSolid(x, y, z - 1)
                || side == 4 && supportQuery.isBlockSolid(x + 1, y, z)
                || side == 5 && supportQuery.isBlockSolid(x - 1, y, z);
    }

    public boolean canPlace(SupportQuery supportQuery, int x, int y, int z) {
        return supportQuery.isBlockSolid(x - 1, y, z)
                || supportQuery.isBlockSolid(x + 1, y, z)
                || supportQuery.isBlockSolid(x, y, z - 1)
                || supportQuery.isBlockSolid(x, y, z + 1);
    }

    public int resolvePostPlaceData(SupportQuery supportQuery, int x, int y, int z, int clickedSide, int currentData) {
        int poweredBit = currentData & 8;
        int facing = currentData & 7;

        if (clickedSide == 2 && supportQuery.isBlockSolid(x, y, z + 1)) {
            facing = 4;
        } else if (clickedSide == 3 && supportQuery.isBlockSolid(x, y, z - 1)) {
            facing = 3;
        } else if (clickedSide == 4 && supportQuery.isBlockSolid(x + 1, y, z)) {
            facing = 2;
        } else if (clickedSide == 5 && supportQuery.isBlockSolid(x - 1, y, z)) {
            facing = 1;
        } else {
            facing = resolveFallbackFacing(supportQuery, x, y, z);
        }

        return composeData(facing, poweredBit);
    }

    public int resolveFallbackFacing(SupportQuery supportQuery, int x, int y, int z) {
        return supportQuery.isBlockSolid(x - 1, y, z)
                ? 1
                : (supportQuery.isBlockSolid(x + 1, y, z)
                ? 2
                : (supportQuery.isBlockSolid(x, y, z - 1)
                ? 3
                : (supportQuery.isBlockSolid(x, y, z + 1) ? 4 : 1)));
    }

    public boolean isAttachedSupportMissing(SupportQuery supportQuery, int x, int y, int z, int blockData) {
        int facing = extractFacing(blockData);
        return facing == 1 && !supportQuery.isBlockSolid(x - 1, y, z)
                || facing == 2 && !supportQuery.isBlockSolid(x + 1, y, z)
                || facing == 3 && !supportQuery.isBlockSolid(x, y, z - 1)
                || facing == 4 && !supportQuery.isBlockSolid(x, y, z + 1);
    }

    public Bounds resolveBounds(int blockData) {
        int facing = extractFacing(blockData);
        boolean pressed = isPressed(blockData);
        float minCenter = 0.375F;
        float maxCenter = 0.625F;
        float radius = 0.1875F;
        float depth = pressed ? 0.0625F : 0.125F;

        if (facing == 1) {
            return new Bounds(0.0F, minCenter, 0.5F - radius, depth, maxCenter, 0.5F + radius);
        }
        if (facing == 2) {
            return new Bounds(1.0F - depth, minCenter, 0.5F - radius, 1.0F, maxCenter, 0.5F + radius);
        }
        if (facing == 3) {
            return new Bounds(0.5F - radius, minCenter, 0.0F, 0.5F + radius, maxCenter, depth);
        }
        if (facing == 4) {
            return new Bounds(0.5F - radius, minCenter, 1.0F - depth, 0.5F + radius, maxCenter, 1.0F);
        }
        return null;
    }

    public int extractFacing(int blockData) {
        return blockData & 7;
    }

    public boolean isPressed(int blockData) {
        return (blockData & 8) > 0;
    }

    public int calculateTogglePowerBit(int blockData) {
        return 8 - (blockData & 8);
    }

    public int composeData(int facing, int powerBit) {
        return facing + powerBit;
    }

    public boolean isPoweringSide(int blockData, int side) {
        if (!isPressed(blockData)) {
            return false;
        }

        int facing = extractFacing(blockData);
        return facing == 5 && side == 1
                || facing == 4 && side == 2
                || facing == 3 && side == 3
                || facing == 2 && side == 4
                || facing == 1 && side == 5;
    }

    public NeighborOffset resolveAttachmentOffset(int facing) {
        if (facing == 1) {
            return new NeighborOffset(-1, 0, 0);
        }
        if (facing == 2) {
            return new NeighborOffset(1, 0, 0);
        }
        if (facing == 3) {
            return new NeighborOffset(0, 0, -1);
        }
        if (facing == 4) {
            return new NeighborOffset(0, 0, 1);
        }
        return new NeighborOffset(0, -1, 0);
    }

    public interface SupportQuery {
        boolean isBlockSolid(int x, int y, int z);
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
