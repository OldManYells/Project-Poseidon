package com.legacyminecraft.poseidon.block;

/**
 * Canonical state, bounds, and texture policy for legacy door wrappers.
 */
public final class DoorStateBehaviour {
    private static final DoorStateBehaviour INSTANCE = new DoorStateBehaviour();

    private DoorStateBehaviour() {
    }

    public static DoorStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureIndex(int side, int blockData, int textureId) {
        if (side == 0 || side == 1) {
            return textureId;
        }

        int orientation = resolveBoundingOrientation(blockData);
        if ((orientation == 0 || orientation == 2) ^ side <= 3) {
            return textureId;
        }

        int textureVariant = orientation / 2 + ((side & 1) ^ orientation);
        textureVariant += (blockData & 4) / 4;
        int resolvedTexture = textureId - (blockData & 8) * 2;
        if ((textureVariant & 1) != 0) {
            resolvedTexture = -resolvedTexture;
        }

        return resolvedTexture;
    }

    public Bounds resolveBounds(int orientation) {
        float thickness = 0.1875F;
        Bounds bounds = new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
        if (orientation == 0) {
            bounds = new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, thickness);
        }
        if (orientation == 1) {
            bounds = new Bounds(1.0F - thickness, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        if (orientation == 2) {
            bounds = new Bounds(0.0F, 0.0F, 1.0F - thickness, 1.0F, 1.0F, 1.0F);
        }
        if (orientation == 3) {
            bounds = new Bounds(0.0F, 0.0F, 0.0F, thickness, 1.0F, 1.0F);
        }
        return bounds;
    }

    public int resolveBoundingOrientation(int blockData) {
        return (blockData & 4) == 0 ? blockData - 1 & 3 : blockData & 3;
    }

    public boolean isOpen(int blockData) {
        return (blockData & 4) != 0;
    }

    public boolean isUpperHalf(int blockData) {
        return (blockData & 8) != 0;
    }

    public int toggleOpenBit(int blockData) {
        return blockData ^ 4;
    }

    public int resolveUpperDataFromLowerToggle(int lowerHalfData) {
        return toggleOpenBit(lowerHalfData) + 8;
    }

    public int resolveDropItemId(int blockData, boolean ironDoor, int ironDoorItemId, int woodDoorItemId) {
        return isUpperHalf(blockData) ? 0 : (ironDoor ? ironDoorItemId : woodDoorItemId);
    }

    public boolean canPlace(int y, boolean hasSupportBelow, boolean canPlaceLowerHalf, boolean canPlaceUpperHalf) {
        return y < 127 && hasSupportBelow && canPlaceLowerHalf && canPlaceUpperHalf;
    }

    public boolean shouldRemoveUpperHalf(int blockBelowTypeId, int doorBlockId) {
        return blockBelowTypeId != doorBlockId;
    }

    public boolean shouldRemoveLowerHalf(int blockAboveTypeId, boolean hasSupportBelow, int doorBlockId) {
        return blockAboveTypeId != doorBlockId || !hasSupportBelow;
    }

    public boolean shouldAlsoRemoveUpperHalfWhenLowerRemoved(boolean lowerRemovedForSupport, int blockAboveTypeId, int doorBlockId) {
        return lowerRemovedForSupport && blockAboveTypeId == doorBlockId;
    }

    public static boolean isOpenStatic(int blockData) {
        return (blockData & 4) != 0;
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
