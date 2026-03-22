package com.legacyminecraft.poseidon.block;

import java.util.Random;

/**
 * Canonical spread/placement rules for legacy mushroom wrappers.
 */
public final class MushroomStateBehaviour {
    private static final MushroomStateBehaviour INSTANCE = new MushroomStateBehaviour();

    private MushroomStateBehaviour() {
    }

    public static MushroomStateBehaviour getInstance() {
        return INSTANCE;
    }

    public boolean shouldAttemptSpread(Random random) {
        return random.nextInt(100) == 0;
    }

    public int resolveHorizontalOffset(Random random) {
        return random.nextInt(3) - 1;
    }

    public int resolveVerticalOffset(Random random) {
        return random.nextInt(2) - random.nextInt(2);
    }

    public boolean canPlantOn(boolean opaqueCubeBelow) {
        return opaqueCubeBelow;
    }

    public boolean canStay(int y, int maxHeight, int lightLevel, int maxLightLevel, boolean validSupport) {
        return y >= 0 && y < maxHeight && lightLevel < maxLightLevel && validSupport;
    }
}
