package com.legacyminecraft.poseidon.block;

/**
 * Canonical placement, attachment, and open-state policy for legacy trapdoor wrappers.
 */
public final class TrapdoorStateBehaviour {
    private static final TrapdoorStateBehaviour INSTANCE = new TrapdoorStateBehaviour();

    private TrapdoorStateBehaviour() {
    }

    public static TrapdoorStateBehaviour getInstance() {
        return INSTANCE;
    }

    public Bounds resolveBounds(int blockData) {
        float thickness = 0.1875F;
        Bounds bounds = new Bounds(0.0F, 0.0F, 0.0F, 1.0F, thickness, 1.0F);
        if (isOpen(blockData)) {
            int facing = blockData & 3;
            if (facing == 0) {
                bounds = new Bounds(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F);
            }
            if (facing == 1) {
                bounds = new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness);
            }
            if (facing == 2) {
                bounds = new Bounds(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }
            if (facing == 3) {
                bounds = new Bounds(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F);
            }
        }
        return bounds;
    }

    public boolean isOpen(int blockData) {
        return (blockData & 4) != 0;
    }

    public int toggleOpenBit(int blockData) {
        return blockData ^ 4;
    }

    public boolean shouldToggleOpenState(int blockData, boolean targetOpenState) {
        return isOpen(blockData) != targetOpenState;
    }

    public int resolvePostPlaceData(int clickedSide) {
        byte facing = 0;
        if (clickedSide == 2) {
            facing = 0;
        }
        if (clickedSide == 3) {
            facing = 1;
        }
        if (clickedSide == 4) {
            facing = 2;
        }
        if (clickedSide == 5) {
            facing = 3;
        }
        return facing;
    }

    public boolean canPlaceOnSide(SupportQuery supportQuery, int x, int y, int z, int side) {
        if (side == 0 || side == 1) {
            return false;
        }

        if (side == 2) {
            ++z;
        }

        if (side == 3) {
            --z;
        }

        if (side == 4) {
            ++x;
        }

        if (side == 5) {
            --x;
        }

        return supportQuery.isBlockSolid(x, y, z);
    }

    public AttachmentOffset resolveAttachmentOffset(int blockData) {
        int facing = blockData & 3;
        if (facing == 0) {
            return new AttachmentOffset(0, 0, 1);
        }
        if (facing == 1) {
            return new AttachmentOffset(0, 0, -1);
        }
        if (facing == 2) {
            return new AttachmentOffset(1, 0, 0);
        }
        return new AttachmentOffset(-1, 0, 0);
    }

    public interface SupportQuery {
        boolean isBlockSolid(int x, int y, int z);
    }

    public static final class AttachmentOffset {
        private final int x;
        private final int y;
        private final int z;

        public AttachmentOffset(int x, int y, int z) {
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
