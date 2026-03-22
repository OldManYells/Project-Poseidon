package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical texture, ignition, and fuse policy for legacy TNT wrappers.
 */
public final class TntStateBehaviour {
    private static final TntStateBehaviour INSTANCE = new TntStateBehaviour();

    private TntStateBehaviour() {
    }

    public static TntStateBehaviour getInstance() {
        return INSTANCE;
    }

    public int resolveTextureBySide(int side, int textureId) {
        return side == 0 ? textureId + 2 : (side == 1 ? textureId + 1 : textureId);
    }

    public boolean shouldPrimeOnPlacement(boolean indirectlyPowered) {
        return indirectlyPowered;
    }

    public boolean shouldPrimeOnPhysics(int neighborTypeId, boolean neighborIsPowerSource, boolean indirectlyPowered) {
        return neighborTypeId > 0 && neighborIsPowerSource && indirectlyPowered;
    }

    public boolean shouldDropAsItem(int breakData) {
        return (breakData & 1) == 0;
    }

    public boolean shouldPrimeOnPostBreak(int breakData) {
        return !shouldDropAsItem(breakData);
    }

    public boolean shouldMarkIgnitedFromHeldItem(int heldItemId, int flintAndSteelItemId) {
        return heldItemId == flintAndSteelItemId;
    }

    public double resolveCenteredSpawnCoordinate(int coordinate) {
        return (double) ((float) coordinate + 0.5F);
    }

    public int resolveDispensedFuseTicks(Random random, int defaultFuseTicks) {
        return random.nextInt(defaultFuseTicks / 4) + defaultFuseTicks / 8;
    }
}
