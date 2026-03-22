package com.legacyminecraft.poseidon.block;

import net.minecraft.server.AxisAlignedBB;

/**
 * Canonical state and geometry policy for legacy cake block wrappers.
 */
public final class CakeStateBehaviour {
    private static final CakeStateBehaviour INSTANCE = new CakeStateBehaviour();

    private CakeStateBehaviour() {
    }

    public static CakeStateBehaviour getInstance() {
        return INSTANCE;
    }

    public Bounds resolveSelectionBounds(int bites) {
        float edgeInset = 0.0625F;
        float biteOffset = (float) (1 + bites * 2) / 16.0F;
        float height = 0.5F;
        return new Bounds(biteOffset, 0.0F, edgeInset, 1.0F - edgeInset, height, 1.0F - edgeInset);
    }

    public AxisAlignedBB resolveCollisionBox(int x, int y, int z, int bites) {
        float edgeInset = 0.0625F;
        float biteOffset = (float) (1 + bites * 2) / 16.0F;
        float height = 0.5F;
        return AxisAlignedBB.b(
                (double) ((float) x + biteOffset),
                (double) y,
                (double) ((float) z + edgeInset),
                (double) ((float) (x + 1) - edgeInset),
                (double) ((float) y + height - edgeInset),
                (double) ((float) (z + 1) - edgeInset)
        );
    }

    public int resolveTextureBySideAndBites(int side, int bites, int textureId) {
        return side == 1
                ? textureId
                : (side == 0 ? textureId + 3 : (bites > 0 && side == 4 ? textureId + 2 : textureId + 1));
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 1 ? textureId : (side == 0 ? textureId + 3 : textureId + 1);
    }

    public boolean canEatAtHealth(int health) {
        return health < 20;
    }

    public int healAmountPerBite() {
        return 3;
    }

    public int incrementBites(int currentBites) {
        return currentBites + 1;
    }

    public boolean isConsumed(int bitesAfterEating) {
        return bitesAfterEating >= 6;
    }

    public boolean canRemainPlaced(boolean superCanPlace, boolean supportBuildable) {
        return superCanPlace && supportBuildable;
    }

    public boolean hasBuildableSupportBelow(boolean supportBuildable) {
        return supportBuildable;
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
