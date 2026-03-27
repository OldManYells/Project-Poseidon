package com.legacyminecraft.poseidon.block;

import com.legacyminecraft.poseidon.world.TileEntity;

/**
 * Canonical bounds/attachment/tile-entity policy for legacy sign wrappers.
 */
public final class SignStateBehaviour {
    private static final SignStateBehaviour INSTANCE = new SignStateBehaviour();

    private SignStateBehaviour() {
    }

    public static SignStateBehaviour getInstance() {
        return INSTANCE;
    }

    public Bounds resolveWallBounds(int data) {
        float f = 0.28125F;
        float f1 = 0.78125F;
        float f2 = 0.0F;
        float f3 = 1.0F;
        float thickness = 0.125F;

        if (data == 2) {
            return new Bounds(f2, f, 1.0F - thickness, f3, f1, 1.0F);
        }
        if (data == 3) {
            return new Bounds(f2, f, 0.0F, f3, f1, thickness);
        }
        if (data == 4) {
            return new Bounds(1.0F - thickness, f, f2, 1.0F, f1, f3);
        }
        if (data == 5) {
            return new Bounds(0.0F, f, f2, thickness, f1, f3);
        }
        return fullBounds();
    }

    public Bounds fullBounds() {
        return new Bounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
    }

    public boolean shouldDropStandingSign(boolean belowBuildable) {
        return !belowBuildable;
    }

    public boolean hasValidWallAttachment(
            int data,
            boolean southBuildable,
            boolean northBuildable,
            boolean eastBuildable,
            boolean westBuildable
    ) {
        if (data == 2 && southBuildable) {
            return true;
        }
        if (data == 3 && northBuildable) {
            return true;
        }
        if (data == 4 && eastBuildable) {
            return true;
        }
        if (data == 5 && westBuildable) {
            return true;
        }
        return false;
    }

    public boolean shouldDropWallSign(boolean hasValidAttachment) {
        return !hasValidAttachment;
    }

    public TileEntity instantiateTileEntity(Class clazz) {
        try {
            return (TileEntity) clazz.newInstance();
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }

    public int resolveDropItemId(int signItemId) {
        return signItemId;
    }

    public boolean shouldFireRedstoneNeighborEvent(int neighborTypeId, boolean neighborIsPowerSource) {
        return neighborTypeId >= 0 && neighborIsPowerSource;
    }

    public static final class Bounds {
        public final float minX;
        public final float minY;
        public final float minZ;
        public final float maxX;
        public final float maxY;
        public final float maxZ;

        public Bounds(float minX, float minY, float minZ, float maxX, float maxY, float maxZ) {
            this.minX = minX;
            this.minY = minY;
            this.minZ = minZ;
            this.maxX = maxX;
            this.maxY = maxY;
            this.maxZ = maxZ;
        }
    }
}
