package com.legacyminecraft.poseidon.block;

import net.minecraft.server.AxisAlignedBB;

import java.util.Random;

/**
 * Canonical state, geometry, and drop policy for legacy snow-layer wrappers.
 */
public final class SnowLayerStateBehaviour {
    private static final SnowLayerStateBehaviour INSTANCE = new SnowLayerStateBehaviour();

    private SnowLayerStateBehaviour() {
    }

    public static SnowLayerStateBehaviour getInstance() {
        return INSTANCE;
    }

    public AxisAlignedBB resolveCollisionBox(int x, int y, int z, int data, double minX, double minY, double minZ, double maxX, double maxZ) {
        int layer = data & 7;
        return layer >= 3
                ? AxisAlignedBB.b(
                (double) x + minX,
                (double) y + minY,
                (double) z + minZ,
                (double) x + maxX,
                (double) y + 0.5D,
                (double) z + maxZ
        ) : null;
    }

    public float resolveSelectionHeight(int data) {
        int layer = data & 7;
        return (float) (2 * (1 + layer)) / 16.0F;
    }

    public boolean canPlace(int supportTypeId, boolean supportCanHostSnow, boolean supportMaterialSolid) {
        return supportTypeId != 0 && supportCanHostSnow && supportMaterialSolid;
    }

    public int resolveDropItemId(int snowballItemId) {
        return snowballItemId;
    }

    public double resolveDropOffset(Random random, float spread) {
        return (double) (random.nextFloat() * spread) + (double) (1.0F - spread) * 0.5D;
    }
}
