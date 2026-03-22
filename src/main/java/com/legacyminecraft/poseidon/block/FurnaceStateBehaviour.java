package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical orientation/texture/state/drop policy for legacy furnace wrappers.
 */
public final class FurnaceStateBehaviour {
    private static final FurnaceStateBehaviour INSTANCE = new FurnaceStateBehaviour();

    private FurnaceStateBehaviour() {
    }

    public static FurnaceStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveDropItemId(int furnaceBlockId) {
        return furnaceBlockId;
    }

    public int resolveDefaultFacing(
            boolean northOpaque,
            boolean southOpaque,
            boolean westOpaque,
            boolean eastOpaque
    ) {
        int facing = 3;

        if (northOpaque && !southOpaque) {
            facing = 3;
        }

        if (southOpaque && !northOpaque) {
            facing = 2;
        }

        if (westOpaque && !eastOpaque) {
            facing = 5;
        }

        if (eastOpaque && !westOpaque) {
            facing = 4;
        }

        return facing;
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 1 ? textureId + 17 : (side == 0 ? textureId + 17 : (side == 3 ? textureId - 1 : textureId));
    }

    public int resolveBlockIdForBurningState(boolean burning, int burningFurnaceBlockId, int normalFurnaceBlockId) {
        return burning ? burningFurnaceBlockId : normalFurnaceBlockId;
    }

    public int resolvePlacementFacingFromYaw(float yaw) {
        int yawQuadrant = (int) Math.floor((double) (yaw * 4.0F / 360.0F) + 0.5D) & 3;
        if (yawQuadrant == 0) {
            return 2;
        }
        if (yawQuadrant == 1) {
            return 5;
        }
        if (yawQuadrant == 2) {
            return 3;
        }
        return 4;
    }

    public float resolveDropOffset(Random random) {
        return random.nextFloat() * 0.8F + 0.1F;
    }

    public int resolveDropStackChunk(Random random, int remainingCount) {
        int chunkSize = random.nextInt(21) + 10;
        return Math.min(chunkSize, remainingCount);
    }

    public double resolveDropHorizontalMotion(Random random, float spread) {
        return (double) ((float) random.nextGaussian() * spread);
    }

    public double resolveDropVerticalMotion(Random random, float spread, double upwardBias) {
        return (double) ((float) random.nextGaussian() * spread + (float) upwardBias);
    }
}
