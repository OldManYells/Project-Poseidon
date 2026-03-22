package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical record-state/texture/drop policy for legacy jukebox wrappers.
 */
public final class JukeboxStateBehaviour {
    private static final JukeboxStateBehaviour INSTANCE = new JukeboxStateBehaviour();

    private JukeboxStateBehaviour() {
    }

    public static JukeboxStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySide(int side, int textureId) {
        return textureId + (side == 1 ? 1 : 0);
    }

    public boolean hasRecord(int blockData) {
        return blockData != 0;
    }

    public boolean shouldIgnoreClientOperations(boolean worldIsStatic) {
        return worldIsStatic;
    }

    public boolean shouldEjectRecord(int recordItemId) {
        return recordItemId != 0;
    }

    public int clearedRecordItemId() {
        return 0;
    }

    public int insertedBlockData() {
        return 1;
    }

    public int stoppedRecordEffectId() {
        return 1005;
    }

    public float recordDropSpread() {
        return 0.7F;
    }

    public double resolveDropXOffset(Random random, float spread) {
        return (double) (random.nextFloat() * spread) + (double) (1.0F - spread) * 0.5D;
    }

    public double resolveDropYOffset(Random random, float spread) {
        return (double) (random.nextFloat() * spread) + (double) (1.0F - spread) * 0.2D + 0.6D;
    }

    public double resolveDropZOffset(Random random, float spread) {
        return (double) (random.nextFloat() * spread) + (double) (1.0F - spread) * 0.5D;
    }

    public int recordPickupDelayTicks() {
        return 10;
    }
}
